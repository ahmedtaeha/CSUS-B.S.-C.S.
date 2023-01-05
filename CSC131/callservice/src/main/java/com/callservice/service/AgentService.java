package com.callservice.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.callservice.entity.AgentEntity;
import com.callservice.repository.EntityRepository;


@Service
public class AgentService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    // REPOSITORY using JPA
    @Autowired
    private EntityRepository database;

    /**
     * Method to retrieve all entities that are available.
     * 
     * @return
     */
    public List<AgentEntity> getEntities() {
        return database.findAll(Sort.by(Sort.Direction.DESC, "storeId"));
    }

    /**
     * Method to filter entities based on some filter query
     * 
     * @param filter String value (available, busy, preview, loggedout, after)
     * @return List
     */
    public List<AgentEntity> filterEntities(String filter) {
        return database.findAllFilter(filter);
    }

    /**
     * Method to save a single entity
     * 
     * @param entity AgentEntity
     * @return String
     */
    @Transactional
    public String saveEntity(AgentEntity entity) {
        long start = System.currentTimeMillis();
        AgentEntity agent;
        String retVal = "";
        try {
            agent = database.findAgent(entity.getId());

            if (agent != null) {
                agent.updateEntity(entity);
                agent = database.save(agent);
                // logger.info("Updated entity: " + agent.getId());
                retVal = "Updated entity";

            } else {
                agent = database.save(entity);
                // logger.info("Created entity: " + agent);
                retVal = "Created entity";
            }
        } catch (Exception e) {
            e.printStackTrace();
            retVal = "Error"; // FIXME:
        }
        // check if agent exists
        long end = System.currentTimeMillis();
        logger.info("Transaction time: {}ms", (end - start));
        return retVal;
    }

    /**
     * Method to delete a single entity
     * 
     * @param entity AgentEntity
     * @return String
     */
    @Transactional
    public String deleteEntity(String entity) {
        long start = System.currentTimeMillis();
        AgentEntity agent;
        String response = "Not found";
        try {
            // agent = database.findAgent(entity.getId());
            agent = database.findAgent(entity);
            if (agent != null) {
                database.delete(agent);
                response = "Deleted entity";
            }
        } catch (Exception e) {
            e.printStackTrace();
            response = "Error";
        }

        long end = System.currentTimeMillis();
        logger.info("Transaction time: {}ms", (end - start));
        return response;
    }

    @Transactional
    public Map<String, String> deleteAgentEntity(String entity) {
        long start = System.currentTimeMillis();

        Map<String, String> map = new HashMap<>();
        AgentEntity agent;
        String response = "Not found";
        try {
            // agent = database.findAgent(entity.getId());
            agent = database.findAgent(entity);
            if (agent != null) {
                map.put("id", agent.getId());
                map.put("status", agent.getStatus());
                database.delete(agent);
                response = "Deleted entity";
            }
        } catch (Exception e) {
            e.printStackTrace();
            response = "Error";
        }
        map.put("response", response);


        long end = System.currentTimeMillis();
        logger.info("Transaction time: {}ms", (end - start));
        return map;
    }

    /**
     * Method to save an array of entities; attempt at handling database concurrency issues
     * 
     * @param entities
     * @return Future object containing a List collection with AgentEntity objects.
     */
    @Async
    @Transactional
    public CompletableFuture<List<AgentEntity>> saveEntities(List<AgentEntity> entities) {
        AgentEntity agent;
        List<AgentEntity> result = new ArrayList<>();
        long start = System.currentTimeMillis();
        // iterate through each assuring not in db
        for (int i = 0; i < entities.size(); i++) {
            try {
                agent = database.findAgent(entities.get(i).getId());

                if (agent != null) {
                    agent.setName(entities.get(i).getName());
                    agent.setStatus(entities.get(i).getStatus());
                    agent = database.saveAndFlush(agent);
                    logger.info("Updated entity: {}", agent.getId());
                } else {
                    agent = database.saveAndFlush(entities.get(i));
                    logger.info("Created entity: {}", agent.getId());
                }
                result.add(agent);
                // database.flush(); // prevents some warning for now; will see in production
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
                logger.error("Error updating/creating entity.");
            }
        }
        long end = System.currentTimeMillis();
        logger.info("Saving/Updating users time: {}ms", (end - start));

        return CompletableFuture.completedFuture(result);
    }


}


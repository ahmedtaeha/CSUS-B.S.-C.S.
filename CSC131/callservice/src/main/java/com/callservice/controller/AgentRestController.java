package com.callservice.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.callservice.entity.AgentEntity;
import com.callservice.service.AgentService;
import com.callservice.utils.ValidatorHelper;


/**
 * Rest API Controller - For sending updates for new agents.
 */
@RestController
// @Validated
@RequestMapping("/api/v1")
public class AgentRestController {
    Logger logger = LoggerFactory.getLogger(AgentRestController.class);

    @Autowired
    private AgentService agentService;

    // set up emitter to transmit calls
    private List<SseEmitter> emitters = new CopyOnWriteArrayList<>();

    /**
     * Method to delete an entity with an associated ID.
     * 
     * @param entityID
     * @return
     */
    @RequestMapping(value = "/agent", method = RequestMethod.DELETE)
    public ResponseEntity<Map<String, String>> deleteEntity(@RequestParam(name = "id", required = true) @NotBlank @Size(min = 1) String entityID) {
        logger.debug("API Invoked: deleteEntity()");
        Map<String, String> ret = new HashMap<>();


        
        // String deleteResponse = agentService.deleteEntity(entityID);
        Map<String, String> deleteResponse = agentService.deleteAgentEntity(entityID);
        ret.put("message", deleteResponse.get("response"));
        if (!ret.get("message").equalsIgnoreCase("deleted entity")) {
            return new ResponseEntity<>(ret, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        // parse the incoming body request assure proper fields
        for (SseEmitter emitter : emitters) {
            try {
                // send an event for each emmitter that is open
                emitter.send(SseEmitter.event().name("deleteAgent").data(deleteResponse));
            } catch (IOException e) {
                emitters.remove(emitter); // handler for io exception
            } catch (Exception e) {
                // handle exception
                e.printStackTrace();
            }
        }

        return new ResponseEntity<>(ret, HttpStatus.OK);


    }

    /**
     * Method to filter out employees based off of a specific filter string. API CALL
     * 
     * @param filter
     * @return
     */
    @RequestMapping(value = "/agent", method = RequestMethod.GET)
    public ResponseEntity<Map<String, Object>> filterAgents(
            @RequestParam(name = "status", required = false) String filter) {
        logger.debug("API Invoked: filterAgents()");
        List<AgentEntity> entities;
        Map<String, Object> ret = new HashMap<>();
        if (filter != null && ValidatorHelper.validFilter(filter)) {
            entities = agentService.filterEntities(filter);
        } else {
            entities = agentService.getEntities();
        }
        ret.put("agents", entities);
        ret.put("response", String.valueOf(HttpStatus.OK.value()));

        return new ResponseEntity<>(ret, HttpStatus.OK);
    }

    /**
     * REST API call for client to send in entities and their status'
     * 
     * @param entity
     * @return
     */
    @RequestMapping(value = "/agent", method = RequestMethod.POST)
    public ResponseEntity<Map<String, String>> saveOrUpdateEntity(@Valid @RequestBody AgentEntity entity) {
        logger.debug("API Invoked: saveOrUpdateEntity()");
        Map<String, String> ret = new HashMap<>();
        ResponseEntity<Map<String, String>> response;
        
        // Check if statuses are valid
        if (!ValidatorHelper.validFilter(entity.getStatus()))
            entity.setStatus("available");


        // parse the incoming body request assure proper fields
        for (SseEmitter emitter : emitters) {
            try {
                // send an event for each emmitter that is open
                emitter.send(SseEmitter.event().name("updateAgent").data(entity));
            } catch (IOException e) {
                emitters.remove(emitter); // handler for io exception
            } catch (Exception e) {
                // handle exception
                e.printStackTrace();
            }
        }

        try {
            String tmp = agentService.saveEntity(entity);
            ret.put("message", tmp);
            ret.put("response", String.valueOf(HttpStatus.OK.value()));
            response = new ResponseEntity<>(ret, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            ret.put("message", e.getMessage());
            ret.put("response", String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
            response = new ResponseEntity<>(ret, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return response;

    }


    /**
     * Server Event Initializer, creates new Emitters when a new request is sent on our frontend.
     * 
     * @return
     */
    @RequestMapping("/init")
    public SseEmitter agents() {
        SseEmitter sseEmitter = new SseEmitter((long) (60000 * 5 )); // add a 5 minute timeout
        // SseEmitter sseEmitter = new SseEmitter(Long.MAX_VALUE);

        try {
            sseEmitter.send(SseEmitter.event().name("INIT"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        // define callback
        sseEmitter.onCompletion(() -> emitters.remove(sseEmitter));
        sseEmitter.onTimeout(() -> emitters.remove(sseEmitter));
        emitters.add(sseEmitter);

        return sseEmitter;
    }


} 

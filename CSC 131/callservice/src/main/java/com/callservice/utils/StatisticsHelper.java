package com.callservice.utils;

import java.util.List;

import com.callservice.entity.AgentEntity;

public class StatisticsHelper {
    
    private static List<AgentEntity> entityPopulation;

    private int allAvailable, allBusy, allPreview, allAfter, allLoggedOut;

    public StatisticsHelper(List<AgentEntity> info) {
        entityPopulation = info;

        for (AgentEntity agent: info) {
            if (agent.getStatus().equalsIgnoreCase("available")) allAvailable++;
            if (agent.getStatus().equalsIgnoreCase("Busy")) allBusy++;
            if (agent.getStatus().equalsIgnoreCase("Preview")) allPreview++;
            if (agent.getStatus().equalsIgnoreCase("After")) allAfter++;
            if (agent.getStatus().equalsIgnoreCase("LoggedOut") || agent.getStatus().equalsIgnoreCase("logged-out")) allLoggedOut++;
        }


    }

    public static List<AgentEntity> getAllEntities() {
        return entityPopulation;
    }
    public static int getAll() {
        return entityPopulation.size();

    }

    // GETTERS
    public int getAllAvailable() {
        return allAvailable;
    }

    public int getAllBusy() {
        return allBusy;
    }

    public int getAllPreview() {
        return allPreview;
    }

    public int getAllAfter() {
        return allAfter;
    }

    public int getAllLoggedOut() {
        return allLoggedOut;
    }



}

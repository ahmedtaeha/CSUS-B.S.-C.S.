package com.callservice.repository;

import java.util.List;
import java.util.concurrent.CompletableFuture;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;
import com.callservice.entity.AgentEntity;


@Repository
public interface EntityRepository extends JpaRepository<AgentEntity, Integer> {

    @Query("select a from AgentEntity a where a.id = ?1")
    public AgentEntity findAgent(String id);

    @Async
    @Query("select a from AgentEntity a where a.id = ?1")
    public CompletableFuture<AgentEntity> findAgentAsync(String id);

    @Query("select a from AgentEntity a where a.status = ?1 order by a.storeId desc")
    public List<AgentEntity> findAllFilter(String filter);

    @Async
    @Query("select a from AgentEntity a where a.status = ?1")
    public CompletableFuture<List<AgentEntity>> findAllFilterAsync(String filter);

}

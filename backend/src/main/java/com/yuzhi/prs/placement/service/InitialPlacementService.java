package com.yuzhi.prs.placement.service;

import com.yuzhi.prs.placement.domain.InitialPlacementPlan;
import com.yuzhi.prs.placement.domain.PlacementBatch;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Service;

@Service
public class InitialPlacementService {

    private final Map<String, InitialPlacementPlan> plans = new ConcurrentHashMap<>();

    public InitialPlacementPlan createPlan(String planId, String projectId, int plannedAssetCount) {
        InitialPlacementPlan plan = new InitialPlacementPlan(
            planId,
            projectId,
            plannedAssetCount,
            "INITIAL_PLACEMENT",
            "PLANNED"
        );
        plans.put(plan.id(), plan);
        return plan;
    }

    public PlacementBatch startExecution(InitialPlacementPlan plan) {
        return new PlacementBatch(
            plan.id() + "-BATCH",
            plan.id(),
            plan.projectId(),
            plan.plannedAssetCount(),
            "LIVE_ON_SITE",
            "IN_EXECUTION"
        );
    }
}

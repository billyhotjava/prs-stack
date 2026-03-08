package com.yuzhi.prs.placement.web;

import com.yuzhi.prs.placement.domain.InitialPlacementPlan;
import com.yuzhi.prs.placement.domain.PlacementBatch;
import com.yuzhi.prs.placement.service.InitialPlacementService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/placements/initial")
public class InitialPlacementResource {

    private final InitialPlacementService initialPlacementService;

    public InitialPlacementResource(InitialPlacementService initialPlacementService) {
        this.initialPlacementService = initialPlacementService;
    }

    @PostMapping("/plans")
    @ResponseStatus(HttpStatus.CREATED)
    public InitialPlacementPlan createPlan(@RequestBody CreateInitialPlacementPlanRequest request) {
        return initialPlacementService.createPlan(request.planId(), request.projectId(), request.plannedAssetCount());
    }

    @PostMapping("/plans/{planId}/execute")
    public PlacementBatch execute(@PathVariable String planId, @RequestBody ExecuteInitialPlacementRequest request) {
        InitialPlacementPlan plan = initialPlacementService.createPlan(planId, request.projectId(), request.assetCount());
        return initialPlacementService.startExecution(plan);
    }

    public record CreateInitialPlacementPlanRequest(
        String planId,
        String projectId,
        int plannedAssetCount
    ) {}

    public record ExecuteInitialPlacementRequest(
        String projectId,
        int assetCount
    ) {}
}

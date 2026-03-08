package com.yuzhi.prs.placement.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.yuzhi.prs.placement.domain.InitialPlacementPlan;
import com.yuzhi.prs.placement.domain.PlacementBatch;
import org.junit.jupiter.api.Test;

class InitialPlacementServiceTest {

    @Test
    void startsTheOnSiteLifecycleFromInitialPlacement() {
        InitialPlacementService service = new InitialPlacementService();

        InitialPlacementPlan plan = service.createPlan("PLAN-001", "PRJ-001", 24);
        PlacementBatch batch = service.startExecution(plan);

        assertThat(plan.lifecycleStage()).isEqualTo("INITIAL_PLACEMENT");
        assertThat(plan.status()).isEqualTo("PLANNED");
        assertThat(batch.planId()).isEqualTo("PLAN-001");
        assertThat(batch.projectId()).isEqualTo("PRJ-001");
        assertThat(batch.assetCount()).isEqualTo(24);
        assertThat(batch.lifecycleStage()).isEqualTo("LIVE_ON_SITE");
        assertThat(batch.status()).isEqualTo("IN_EXECUTION");
    }
}

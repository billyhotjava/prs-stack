package com.yuzhi.prs.change.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.yuzhi.prs.change.domain.ApprovalDecision;
import com.yuzhi.prs.change.domain.PlantChangeRequest;
import org.junit.jupiter.api.Test;

class PlantChangeServiceTest {

    @Test
    void requiresSupervisorApprovalForFieldGeneratedPlantChanges() {
        PlantChangeService service = new PlantChangeService();

        PlantChangeRequest draft = service.createDraft(
            "CHG-001",
            "PRJ-001",
            "POS-001",
            "AST-001",
            "replacement",
            "photo+voice",
            "Replace the ficus after repeated browning"
        );
        PlantChangeRequest submitted = service.submitForApproval(draft);
        PlantChangeRequest approved = service.recordDecision(
            submitted,
            new ApprovalDecision("CHG-001", "SUP-001", "Zhang Min", "approved", "Proceed with replacement")
        );

        assertThat(draft.status()).isEqualTo("draft");
        assertThat(draft.supervisorApprovalRequired()).isTrue();
        assertThat(submitted.status()).isEqualTo("pending-supervisor-approval");
        assertThat(approved.status()).isEqualTo("approved");
        assertThat(approved.approvalDecision().decision()).isEqualTo("approved");
    }
}

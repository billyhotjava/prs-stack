package com.yuzhi.prs.project.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.yuzhi.prs.customer.domain.CustomerContact;
import com.yuzhi.prs.project.domain.Position;
import org.junit.jupiter.api.Test;

class PositionServiceTest {

    @Test
    void createsOperationalAnchorForProjectPosition() {
        PositionService service = new PositionService();
        CustomerContact primaryContact = new CustomerContact(
            "CONTACT-001",
            "CUS-001",
            "Ms. Lin",
            "13900000000"
        );

        Position position = service.registerPosition(
            "PRJ-001",
            "Tower A",
            "18F",
            "POS-1801",
            "Reception East",
            primaryContact
        );

        assertThat(position.projectId()).isEqualTo("PRJ-001");
        assertThat(position.building().name()).isEqualTo("Tower A");
        assertThat(position.floor().name()).isEqualTo("18F");
        assertThat(position.code()).isEqualTo("POS-1801");
        assertThat(position.displayName()).isEqualTo("Reception East");
        assertThat(position.primaryContact().name()).isEqualTo("Ms. Lin");
    }
}

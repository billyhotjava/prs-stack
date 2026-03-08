package com.yuzhi.prs.asset.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.yuzhi.prs.asset.domain.AssetMovement;
import com.yuzhi.prs.asset.domain.PlantAsset;
import java.util.List;
import org.junit.jupiter.api.Test;

class AssetStateServiceTest {

    @Test
    void tracksStateTransitionsForPlacementReplacementAndRecovery() {
        AssetStateService service = new AssetStateService();

        PlantAsset stocked = service.createAsset("AST-001", "Ficus lyrata");
        PlantAsset placed = service.placeOnSite(stocked, "PRJ-001", "POS-1801");
        PlantAsset replacementPending = service.markReplacementRequired(placed, "leaf-browning");
        PlantAsset recovered = service.recoverToNursery(replacementPending);
        List<AssetMovement> movements = service.listMovements("AST-001");

        assertThat(stocked.status()).isEqualTo("IN_STOCK");
        assertThat(placed.status()).isEqualTo("ON_SITE");
        assertThat(placed.projectId()).isEqualTo("PRJ-001");
        assertThat(placed.positionId()).isEqualTo("POS-1801");
        assertThat(replacementPending.status()).isEqualTo("PENDING_REPLACEMENT");
        assertThat(recovered.status()).isEqualTo("RECOVERED");
        assertThat(movements).extracting(AssetMovement::movementType)
            .containsExactly("PLACE_ON_SITE", "REQUEST_REPLACEMENT", "RECOVER_TO_NURSERY");
    }
}

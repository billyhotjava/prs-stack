package com.yuzhi.prs.asset.service;

import com.yuzhi.prs.asset.domain.AssetMovement;
import com.yuzhi.prs.asset.domain.PlantAsset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Service;

@Service
public class AssetStateService {

    private final Map<String, List<AssetMovement>> movementLog = new ConcurrentHashMap<>();

    public PlantAsset createAsset(String assetId, String plantName) {
        return new PlantAsset(assetId, plantName, null, null, "IN_STOCK");
    }

    public PlantAsset placeOnSite(PlantAsset asset, String projectId, String positionId) {
        PlantAsset placed = new PlantAsset(asset.id(), asset.plantName(), projectId, positionId, "ON_SITE");
        recordMovement(placed, "PLACE_ON_SITE", asset.status(), placed.status(), "initial placement");
        return placed;
    }

    public PlantAsset markReplacementRequired(PlantAsset asset, String reason) {
        PlantAsset replacementPending = new PlantAsset(
            asset.id(),
            asset.plantName(),
            asset.projectId(),
            asset.positionId(),
            "PENDING_REPLACEMENT"
        );
        recordMovement(replacementPending, "REQUEST_REPLACEMENT", asset.status(), replacementPending.status(), reason);
        return replacementPending;
    }

    public PlantAsset recoverToNursery(PlantAsset asset) {
        PlantAsset recovered = new PlantAsset(asset.id(), asset.plantName(), asset.projectId(), asset.positionId(), "RECOVERED");
        recordMovement(recovered, "RECOVER_TO_NURSERY", asset.status(), recovered.status(), "return to nursery");
        return recovered;
    }

    public List<AssetMovement> listMovements(String assetId) {
        return List.copyOf(movementLog.getOrDefault(assetId, List.of()));
    }

    private void recordMovement(
        PlantAsset asset,
        String movementType,
        String fromStatus,
        String toStatus,
        String note
    ) {
        movementLog.computeIfAbsent(asset.id(), ignored -> new ArrayList<>())
            .add(new AssetMovement(
                asset.id(),
                movementType,
                fromStatus,
                toStatus,
                asset.projectId(),
                asset.positionId(),
                note
            ));
    }
}

package com.yuzhi.prs.project.service;

import com.yuzhi.prs.customer.domain.CustomerContact;
import com.yuzhi.prs.project.domain.Building;
import com.yuzhi.prs.project.domain.Floor;
import com.yuzhi.prs.project.domain.Position;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Service;

@Service
public class PositionService {

    private final Map<String, Position> positions = new ConcurrentHashMap<>();

    public Position registerPosition(
        String projectId,
        String buildingName,
        String floorName,
        String positionCode,
        String displayName,
        CustomerContact primaryContact
    ) {
        Building building = new Building(projectId + "-" + buildingName, projectId, buildingName);
        Floor floor = new Floor(building.id() + "-" + floorName, building.id(), floorName);
        Position position = new Position(
            projectId + "-" + positionCode,
            projectId,
            building,
            floor,
            positionCode,
            displayName,
            primaryContact
        );

        positions.put(position.id(), position);
        return position;
    }
}

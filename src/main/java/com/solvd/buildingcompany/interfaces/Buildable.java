package com.solvd.buildingcompany.interfaces;

import com.solvd.buildingcompany.exceptions.InvalidMaterialException;
import com.solvd.buildingcompany.models.ConstructionTeam;

public interface Buildable {
    double calculateConstructionTime();

    double estimateCost(ConstructionTeam team) throws InvalidMaterialException;

    boolean validateBlueprint();
}

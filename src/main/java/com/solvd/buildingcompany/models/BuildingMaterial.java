package com.solvd.buildingcompany.models;

import com.solvd.buildingcompany.interfaces.CostCalculatable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public record BuildingMaterial(
        String name,
        double pricePerUnit,
        String unitType
) implements CostCalculatable {

    private static final Logger LOGGER = LogManager.getLogger(BuildingMaterial.class);

    public BuildingMaterial {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        if (pricePerUnit <= 0) {
            throw new IllegalArgumentException("Price per unit must be positive");
        }
        if (unitType == null || unitType.trim().isEmpty()) {
            throw new IllegalArgumentException("Unit type cannot be null or empty");
        }
        LOGGER.debug("Building material created: {} (${} per {})", name, pricePerUnit, unitType);
    }

    @Override
    public double calculateCost() {
        // Simplified calculation - in real app would include quantity
        double cost = pricePerUnit * 100; // Assume 100 units
        LOGGER.debug("Material {} cost calculated: ${}", name, cost);
        return cost;
    }

    @Override
    public String toString() {
        return String.format("%s (%.2f per %s)", name, pricePerUnit, unitType);
    }
}

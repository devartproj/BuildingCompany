package com.solvd.buildingcompany.models;

import com.solvd.buildingcompany.interfaces.CostCalculatable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BuildingMaterial implements CostCalculatable {
    private static final Logger LOGGER = LogManager.getLogger(BuildingMaterial.class);

    private String name;
    private double pricePerUnit;
    private String unitType;

    public BuildingMaterial(String name, double pricePerUnit, String unitType) {
        this.name = name;
        this.pricePerUnit = pricePerUnit;
        this.unitType = unitType;
        LOGGER.debug("Building material created: {} (${} per {})", name, pricePerUnit, unitType);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPricePerUnit() {
        return pricePerUnit;
    }

    public void setPricePerUnit(double pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
    }

    public String getUnitType() {
        return unitType;
    }

    public void setUnitType(String unitType) {
        this.unitType = unitType;
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

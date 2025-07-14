package com.solvd.buildingcompany.enums;

public enum BuildingType {
    RESIDENTIAL("Residential Building", 1.0),
    COMMERCIAL("Commercial Building", 1.5),
    INDUSTRIAL("Industrial Building", 2.0),
    EDUCATIONAL("Educational Institution", 1.3),
    HEALTHCARE("Healthcare Facility", 1.8);

    private final String description;
    private final double costMultiplier;

    BuildingType(String description, double costMultiplier) {
        this.description = description;
        this.costMultiplier = costMultiplier;
    }

    public String getDescription() {
        return description;
    }

    public double getCostMultiplier() {
        return costMultiplier;
    }

    @Override
    public String toString() {
        return description + " (cost multiplier: " + costMultiplier + "x)";
    }
}

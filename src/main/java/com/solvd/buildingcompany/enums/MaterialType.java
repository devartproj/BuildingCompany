package com.solvd.buildingcompany.enums;

public enum MaterialType {
    CONCRETE("Concrete", 120.0, "m³"),
    BRICK("Brick", 0.75, "pcs"),
    WOOD("Wood", 400.0, "m³"),
    STEEL("Steel", 2500.0, "ton"),
    GLASS("Glass", 50.0, "m²");

    private final String name;
    private final double baseCost;
    private final String unit;

    MaterialType(String name, double baseCost, String unit) {
        this.name = name;
        this.baseCost = baseCost;
        this.unit = unit;
    }

    public String getName() {
        return name;
    }

    public double getBaseCost() {
        return baseCost;
    }

    public String getUnit() {
        return unit;
    }

    @Override
    public String toString() {
        return name + " (" + baseCost + " USD/" + unit + ")";
    }
}
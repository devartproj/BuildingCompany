
package com.solvd.buildingcompany.enums;

public enum ConstructionPhase {
    PLANNING("Planning", 0),
    SITE_PREPARATION("Site Preparation", 1),
    FOUNDATION("Foundation", 2),
    FRAMING("Framing", 3),
    ROOFING("Roofing", 4),
    PLUMBING("Plumbing", 5),
    ELECTRICAL("Electrical", 6),
    INSULATION("Insulation", 7),
    DRYWALL("Drywall", 8),
    FINISHING("Finishing", 9),
    INSPECTION("Inspection", 10),
    COMPLETED("Completed", 11);

    private final String description;
    private final int order;

    ConstructionPhase(String description, int order) {
        this.description = description;
        this.order = order;
    }

    public String getDescription() {
        return description;
    }

    public int getOrder() {
        return order;
    }

    public boolean comesBefore(ConstructionPhase other) {
        return this.order < other.order;
    }

    public boolean comesAfter(ConstructionPhase other) {
        return this.order > other.order;
    }

    @Override
    public String toString() {
        return description + " (phase " + order + ")";
    }
}
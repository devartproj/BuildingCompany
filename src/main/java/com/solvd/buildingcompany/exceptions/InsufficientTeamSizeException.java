package com.solvd.buildingcompany.exceptions;

/**
 * Exception thrown when a construction team does not have enough members
 */
public class InsufficientTeamSizeException extends Exception {
    private final int actualSize;
    private final int requiredSize;

    public InsufficientTeamSizeException(String message, int actualSize, int requiredSize) {
        super(message);
        this.actualSize = actualSize;
        this.requiredSize = requiredSize;
    }

    public int getActualSize() {
        return actualSize;
    }

    public int getRequiredSize() {
        return requiredSize;
    }
}

package com.solvd.buildingcompany.exceptions;

/**
 * Exception thrown when a project exceeds the maximum allowed size
 */
public class ProjectSizeTooLargeException extends Exception {
    private final double actualSize;
    private final double maxAllowedSize;

    public ProjectSizeTooLargeException(String message, double actualSize, double maxAllowedSize) {
        super(message);
        this.actualSize = actualSize;
        this.maxAllowedSize = maxAllowedSize;
    }

    public double getActualSize() {
        return actualSize;
    }

    public double getMaxAllowedSize() {
        return maxAllowedSize;
    }
}

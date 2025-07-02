package com.solvd.buildingcompany.exceptions;

/**
 * Exception thrown when a project calculation exceeds the available budget
 */
public class BudgetExceededException extends Exception {
    private final double calculatedCost;
    private final double availableBudget;

    public BudgetExceededException(String message, double calculatedCost, double availableBudget) {
        super(message);
        this.calculatedCost = calculatedCost;
        this.availableBudget = availableBudget;
    }

    public double getCalculatedCost() {
        return calculatedCost;
    }

    public double getAvailableBudget() {
        return availableBudget;
    }

    public double getExceededAmount() {
        return calculatedCost - availableBudget;
    }
}

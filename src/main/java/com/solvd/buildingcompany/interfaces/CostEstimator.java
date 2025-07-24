package com.solvd.buildingcompany.interfaces;

@FunctionalInterface
public interface CostEstimator {

     //Calculates estimated cost based on area and a coefficient

    double calculateEstimatedCost(double area, double coefficient);
}

package com.solvd.buildingcompany.interfaces;

/**
 * Functional interface for processing building materials
 */
@FunctionalInterface
public interface MaterialProcessor {
    /**
     * Process a material and return the processed result
     * @param material The input material name
     * @return The processed material result
     */
    String processMaterial(String material);
}

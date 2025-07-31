package com.solvd.buildingcompany.annotations;

import java.util.Arrays;

/**
 * Model class that encapsulates data from BuildingOperation annotations
 */
public class BuildingOperationInfo {
    private final String name;
    private final String description;
    private final Priority priority;
    private final double estimatedCost;
    private final String[] requiredTools;

    private BuildingOperationInfo(Builder builder) {
        this.name = builder.name;
        this.description = builder.description;
        this.priority = builder.priority;
        this.estimatedCost = builder.estimatedCost;
        this.requiredTools = builder.requiredTools;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Priority getPriority() {
        return priority;
    }

    public double getEstimatedCost() {
        return estimatedCost;
    }

    public String[] getRequiredTools() {
        return requiredTools.clone();
    }

    @Override
    public String toString() {
        return "BuildingOperationInfo{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", priority=" + priority +
                ", estimatedCost=" + estimatedCost +
                ", requiredTools=" + Arrays.toString(requiredTools) +
                '}';
    }

    /**
     * Builder pattern for BuildingOperationInfo
     */
    public static class Builder {
        private String name;
        private String description = "";
        private Priority priority = Priority.MEDIUM;
        private double estimatedCost = 0.0;
        private String[] requiredTools = new String[0];

        public Builder(String name) {
            this.name = name;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder priority(Priority priority) {
            this.priority = priority;
            return this;
        }

        public Builder estimatedCost(double estimatedCost) {
            this.estimatedCost = estimatedCost;
            return this;
        }

        public Builder requiredTools(String[] requiredTools) {
            this.requiredTools = requiredTools.clone();
            return this;
        }

        public BuildingOperationInfo build() {
            return new BuildingOperationInfo(this);
        }
    }
}

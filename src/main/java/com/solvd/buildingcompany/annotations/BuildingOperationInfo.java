package com.solvd.buildingcompany.annotations;

import java.util.Arrays;

/**
 * Contains information extracted from BuildingOperation annotation
 */
public class BuildingOperationInfo {
    private final String description;
    private final Priority priority;
    private final double estimatedCost;
    private final String[] requiredTools;
    private final String methodName;
    private final String className;

    /**
     * Constructor with all fields
     *
     * @param description   operation description
     * @param priority      operation priority
     * @param estimatedCost estimated cost of operation
     * @param requiredTools array of required tools
     * @param methodName    name of the method (empty for class-level annotations)
     * @param className     name of the class
     */
    public BuildingOperationInfo(String description,
                                 Priority priority,
                                 double estimatedCost,
                                 String[] requiredTools,
                                 String methodName,
                                 String className) {
        this.description = description;
        this.priority = priority;
        this.estimatedCost = estimatedCost;
        this.requiredTools = requiredTools != null ? requiredTools.clone() : new String[0];
        this.methodName = methodName;
        this.className = className;
    }

    /**
     * Get operation description
     *
     * @return description string
     */
    public String getDescription() {
        return description;
    }

    /**
     * Get operation priority
     *
     * @return priority enum value
     */
    public Priority getPriority() {
        return priority;
    }

    /**
     * Get estimated cost
     *
     * @return estimated cost value
     */
    public double getEstimatedCost() {
        return estimatedCost;
    }

    /**
     * Get array of required tools
     *
     * @return array of tool names
     */
    public String[] getRequiredTools() {
        return requiredTools.clone();
    }

    /**
     * Get method name
     *
     * @return method name or empty string for class-level annotations
     */
    public String getMethodName() {
        return methodName;
    }

    /**
     * Get operation name (either method name or class name)
     * 
     * @return name of the operation (method or class)
     */
    public String getName() {
        return isClassLevel() ? className : methodName;
    }

    /**
     * Get class name
     *
     * @return name of the class
     */
    public String getClassName() {
        return className;
    }

    /**
     * Checks if this is a class-level annotation
     *
     * @return true if class-level, false if method-level
     */
    public boolean isClassLevel() {
        return methodName.isEmpty();
    }

    /**
     * Checks if operation requires specific tool
     *
     * @param toolName name of the tool to check
     * @return true if tool is required, false otherwise
     */
    public boolean requiresTool(String toolName) {
        return Arrays.asList(requiredTools).contains(toolName);
    }

    /**
     * Get number of required tools
     *
     * @return number of required tools
     */
    public int getRequiredToolsCount() {
        return requiredTools.length;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BuildingOperationInfo that = (BuildingOperationInfo) o;

        if (Double.compare(that.estimatedCost, estimatedCost) != 0) return false;
        if (!description.equals(that.description)) return false;
        if (priority != that.priority) return false;
        if (!Arrays.equals(requiredTools, that.requiredTools)) return false;
        if (!methodName.equals(that.methodName)) return false;
        return className.equals(that.className);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = description.hashCode();
        result = 31 * result + priority.hashCode();
        temp = Double.doubleToLongBits(estimatedCost);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + Arrays.hashCode(requiredTools);
        result = 31 * result + methodName.hashCode();
        result = 31 * result + className.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "BuildingOperationInfo{" +
                "className='" + className + '\'' +
                (methodName.isEmpty() ? "" : ", methodName='" + methodName + '\'') +
                ", description='" + description + '\'' +
                ", priority=" + priority +
                ", estimatedCost=" + estimatedCost +
                ", requiredTools=" + Arrays.toString(requiredTools) +
                '}';
    }

    /**
     * Creates a copy of BuildingOperationInfo with modified priority
     *
     * @param newPriority new priority value
     * @return new instance with updated priority
     */
    public BuildingOperationInfo withPriority(Priority newPriority) {
        return new BuildingOperationInfo(
                this.description,
                newPriority,
                this.estimatedCost,
                this.requiredTools,
                this.methodName,
                this.className
        );
    }

    /**
     * Creates a copy of BuildingOperationInfo with modified estimated cost
     *
     * @param newCost new estimated cost value
     * @return new instance with updated cost
     */
    public BuildingOperationInfo withEstimatedCost(double newCost) {
        return new BuildingOperationInfo(
                this.description,
                this.priority,
                newCost,
                this.requiredTools,
                this.methodName,
                this.className
        );
    }
}
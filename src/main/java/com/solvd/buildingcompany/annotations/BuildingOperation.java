package com.solvd.buildingcompany.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.Documented;

/**
 * Annotation to describe building operations in construction projects.
 * Can be applied to both methods and classes.
 *
 * Usage example:
 *
 * @BuildingOperation(
 *     description = "Installation of window frames",
 *     priority = Priority.HIGH,
 *     estimatedCost = 1500.0,
 *     requiredTools = {"level", "drill", "screwdriver"}
 * )
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface BuildingOperation {
    /**
     * Description of the building operation.
     * Should provide clear and concise information about what the operation does.
     *
     * @return operation description
     */
    String description() default "";

    /**
     * Priority level of the operation.
     * Determines the order and importance of the operation in the construction process.
     *
     * @return priority level from Priority enum
     */
    Priority priority() default Priority.MEDIUM;

    /**
     * Estimated cost of the operation in the project's currency.
     * Used for budgeting and cost analysis.
     *
     * @return estimated cost value
     */
    double estimatedCost() default 0.0;

    /**
     * List of tools required to perform the operation.
     * Each string should represent a specific tool needed.
     *
     * @return array of required tool names
     */
    String[] requiredTools() default {};

    /**
     * Optional time estimate in hours for the operation.
     * Used for project scheduling and timeline calculations.
     *
     * @return estimated time in hours
     */
    double estimatedTime() default 0.0;

    /**
     * Indicates whether the operation requires special certification or qualification.
     * Used for safety and compliance purposes.
     *
     * @return true if special certification is required
     */
    boolean requiresCertification() default false;

    /**
     * Optional dependencies on other operations that must be completed first.
     * Specified as array of operation names.
     *
     * @return array of dependent operation names
     */
    String[] dependencies() default {};

    /**
     * Safety risk level associated with the operation.
     * Used for risk assessment and safety planning.
     *
     * @return risk level from 0 (low) to 3 (high)
     */
    int riskLevel() default 0;
}
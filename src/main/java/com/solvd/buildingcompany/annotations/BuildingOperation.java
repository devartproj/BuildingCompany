package com.solvd.buildingcompany.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation for marking building operations with metadata
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface BuildingOperation {
    /**
     * Description of the building operation
     */
    String description() default "";

    /**
     * Priority level of the operation
     */
    Priority priority() default Priority.MEDIUM;

    /**
     * Estimated cost of the operation in dollars
     */
    double estimatedCost() default 0.0;

    /**
     * List of tools required for the operation
     */
    String[] requiredTools() default {};
}

package com.solvd.buildingcompany.utils;

import com.solvd.buildingcompany.annotations.BuildingOperation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * Utility class for processing BuildingOperation annotations at runtime
 */
public class BuildingOperationProcessor {
    private static final Logger LOGGER = LogManager.getLogger(BuildingOperationProcessor.class);

    private BuildingOperationProcessor() {
        // Private constructor to prevent instantiation
    }

    /**
     * Processes all BuildingOperation annotations in a class
     * @param clazz The class to process
     */
    public static void processOperations(Class<?> clazz) {
        LOGGER.info("Processing building operations for class: {}", clazz.getSimpleName());

        // Process class-level annotation
        if (clazz.isAnnotationPresent(BuildingOperation.class)) {
            BuildingOperation annotation = clazz.getAnnotation(BuildingOperation.class);
            processAnnotation(clazz.getSimpleName(), annotation);
        }

        // Process method-level annotations
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(BuildingOperation.class)) {
                BuildingOperation annotation = method.getAnnotation(BuildingOperation.class);
                processAnnotation(method.getName(), annotation);
            }
        }
    }

    private static void processAnnotation(String elementName, BuildingOperation annotation) {
        LOGGER.info("Operation: {}", elementName);

        if (!annotation.description().isEmpty()) {
            LOGGER.info("Description: {}", annotation.description());
        }

        LOGGER.info("Priority: {}", annotation.priority());

        if (annotation.estimatedCost() > 0) {
            LOGGER.info("Estimated Cost: ${}", annotation.estimatedCost());
        }

        if (annotation.requiredTools().length > 0) {
            LOGGER.info("Required Tools: {}", Arrays.toString(annotation.requiredTools()));
        }

        LOGGER.info("------------------------");
    }
}

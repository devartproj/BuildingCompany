package com.solvd.buildingcompany.services;

import com.solvd.buildingcompany.annotations.BuildingOperation;
import com.solvd.buildingcompany.annotations.BuildingOperationInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Service class to encapsulate interactions with BuildingOperation annotations
 */
public class BuildingOperationService {
    private static final Logger LOGGER = LogManager.getLogger(BuildingOperationService.class);

    /**
     * Private constructor to prevent instantiation
     */
    private BuildingOperationService() {
    }

    /**
     * Gets all BuildingOperation annotations from a class and its methods
     *
     * @param clazz The class to analyze
     * @return List of BuildingOperationInfo objects
     */
    public static List<BuildingOperationInfo> getOperationInfo(Class<?> clazz) {
        List<BuildingOperationInfo> operationInfoList = new ArrayList<>();

        // Process class-level annotation
        if (clazz.isAnnotationPresent(BuildingOperation.class)) {
            BuildingOperation annotation = clazz.getAnnotation(BuildingOperation.class);
            operationInfoList.add(convertToInfo(clazz.getSimpleName(), annotation));
        }

        // Process method-level annotations
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(BuildingOperation.class)) {
                BuildingOperation annotation = method.getAnnotation(BuildingOperation.class);
                operationInfoList.add(convertToInfo(method.getName(), annotation));
            }
        }

        return operationInfoList;
    }

    /**
     * Logs detailed information about BuildingOperation annotations in a class
     *
     * @param clazz The class to process
     */
    public static void logOperationInfo(Class<?> clazz) {
        LOGGER.info("Processing building operations for class: {}", clazz.getSimpleName());

        List<BuildingOperationInfo> operationInfoList = getOperationInfo(clazz);

        for (BuildingOperationInfo info : operationInfoList) {
            logOperationInfo(info);
        }
    }

    /**
     * Gets BuildingOperation info for a specific method
     *
     * @param clazz      The class containing the method
     * @param methodName The name of the method
     * @return BuildingOperationInfo or null if not found
     */
    public static BuildingOperationInfo getMethodOperationInfo(Class<?> clazz, String methodName) {
        try {
            Method method = findMethodByName(clazz, methodName);
            if (method != null && method.isAnnotationPresent(BuildingOperation.class)) {
                BuildingOperation annotation = method.getAnnotation(BuildingOperation.class);
                return convertToInfo(methodName, annotation);
            }
        } catch (Exception e) {
            LOGGER.error("Error getting operation info for method: {}", methodName, e);
        }
        return null;
    }

    /**
     * Gets a specific BuildingOperation info for a class
     *
     * @param clazz The class to analyze
     * @return BuildingOperationInfo or null if not found
     */
    public static BuildingOperationInfo getClassOperationInfo(Class<?> clazz) {
        if (clazz.isAnnotationPresent(BuildingOperation.class)) {
            BuildingOperation annotation = clazz.getAnnotation(BuildingOperation.class);
            return convertToInfo(clazz.getSimpleName(), annotation);
        }
        return null;
    }

    /**
     * Gets all high priority operations from a class
     *
     * @param clazz The class to analyze
     * @return List of high priority BuildingOperationInfo objects
     */
    public static List<BuildingOperationInfo> getHighPriorityOperations(Class<?> clazz) {
        List<BuildingOperationInfo> allOperations = getOperationInfo(clazz);
        List<BuildingOperationInfo> highPriorityOps = new ArrayList<>();

        for (BuildingOperationInfo info : allOperations) {
            if (info.getPriority() == com.solvd.buildingcompany.annotations.Priority.HIGH || 
                info.getPriority() == com.solvd.buildingcompany.annotations.Priority.CRITICAL) {
                highPriorityOps.add(info);
            }
        }

        return highPriorityOps;
    }

    /**
     * Gets total estimated cost of all operations in a class
     *
     * @param clazz The class to analyze
     * @return Total estimated cost
     */
    public static double getTotalEstimatedCost(Class<?> clazz) {
        List<BuildingOperationInfo> operations = getOperationInfo(clazz);
        double totalCost = 0.0;

        for (BuildingOperationInfo info : operations) {
            totalCost += info.getEstimatedCost();
        }

        return totalCost;
    }

    /**
     * Private helper method to find a method by name
     */
    private static Method findMethodByName(Class<?> clazz, String methodName) {
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.getName().equals(methodName)) {
                return method;
            }
        }
        return null;
    }

    /**
     * Private helper method to convert annotation to info object
     */
    private static BuildingOperationInfo convertToInfo(String name, BuildingOperation annotation) {
        return new BuildingOperationInfo.Builder(name)
                .description(annotation.description())
                .priority(annotation.priority())
                .estimatedCost(annotation.estimatedCost())
                .requiredTools(annotation.requiredTools())
                .build();
    }

    /**
     * Private helper method to log operation info
     */
    private static void logOperationInfo(BuildingOperationInfo info) {
        LOGGER.info("Operation: {}", info.getName());

        if (!info.getDescription().isEmpty()) {
            LOGGER.info("Description: {}", info.getDescription());
        }

        LOGGER.info("Priority: {}", info.getPriority());

        if (info.getEstimatedCost() > 0) {
            LOGGER.info("Estimated Cost: ${}", info.getEstimatedCost());
        }

        if (info.getRequiredTools().length > 0) {
            LOGGER.info("Required Tools: {}", java.util.Arrays.toString(info.getRequiredTools()));
        }

        LOGGER.info("------------------------");
    }
}

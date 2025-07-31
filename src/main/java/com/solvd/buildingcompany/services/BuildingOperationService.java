package com.solvd.buildingcompany.services;

import com.solvd.buildingcompany.annotations.BuildingOperation;
import com.solvd.buildingcompany.annotations.BuildingOperationInfo;
import com.solvd.buildingcompany.annotations.Priority;
import com.solvd.buildingcompany.annotations.BuildingOperation;
import com.solvd.buildingcompany.annotations.BuildingOperationInfo;
import com.solvd.buildingcompany.annotations.Priority;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Service class to encapsulate interactions with BuildingOperation annotations
 */
public class BuildingOperationService {
    private static final Logger LOGGER = LogManager.getLogger(BuildingOperationService.class);

    /**
     * Logs information about all BuildingOperation annotations in a class
     *
     * @param clazz The class to analyze
     */
    /**
     * Calculates the total estimated cost of all building operations in a class
     *
     * @param clazz The class to analyze
     * @return The total estimated cost
     */
    public static double getTotalEstimatedCost(Class<?> clazz) {
        double totalCost = 0.0;

        // Add class-level annotation cost if present
        BuildingOperation classAnnotation = clazz.getAnnotation(BuildingOperation.class);
        if (classAnnotation != null) {
            totalCost += classAnnotation.estimatedCost();
        }

        // Add method-level annotation costs
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            BuildingOperation methodAnnotation = method.getAnnotation(BuildingOperation.class);
            if (methodAnnotation != null) {
                totalCost += methodAnnotation.estimatedCost();
            }
        }

        return totalCost;
    }

    public static void logOperationInfo(Class<?> clazz) {
        LOGGER.info("Processing building operations for class: {}", clazz.getSimpleName());

        // Get class-level annotation
        BuildingOperation classAnnotation = clazz.getAnnotation(BuildingOperation.class);
        if (classAnnotation != null) {
            LOGGER.info("Class-level operation: {}", clazz.getSimpleName());
            LOGGER.info("Description: {}", classAnnotation.description());
            LOGGER.info("Priority: {}", classAnnotation.priority());
            LOGGER.info("Estimated cost: ${}", classAnnotation.estimatedCost());
            if (classAnnotation.requiredTools().length > 0) {
                LOGGER.info("Required tools: {}", Arrays.toString(classAnnotation.requiredTools()));
            }
            if (classAnnotation.estimatedTime() > 0) {
                LOGGER.info("Estimated time: {} hours", classAnnotation.estimatedTime());
            }
            if (classAnnotation.requiresCertification()) {
                LOGGER.info("Requires certification: yes");
            }
            if (classAnnotation.dependencies().length > 0) {
                LOGGER.info("Dependencies: {}", Arrays.toString(classAnnotation.dependencies()));
            }
            if (classAnnotation.riskLevel() > 0) {
                LOGGER.info("Risk level: {}", classAnnotation.riskLevel());
            }
            LOGGER.info("------------------------");
        }

        // Get method-level annotations
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            BuildingOperation methodAnnotation = method.getAnnotation(BuildingOperation.class);
            if (methodAnnotation != null) {
                LOGGER.info("Method operation: {}", method.getName());
                LOGGER.info("Description: {}", methodAnnotation.description());
                LOGGER.info("Priority: {}", methodAnnotation.priority());
                LOGGER.info("Estimated cost: ${}", methodAnnotation.estimatedCost());
                if (methodAnnotation.requiredTools().length > 0) {
                    LOGGER.info("Required tools: {}", Arrays.toString(methodAnnotation.requiredTools()));
                }
                if (methodAnnotation.estimatedTime() > 0) {
                    LOGGER.info("Estimated time: {} hours", methodAnnotation.estimatedTime());
                }
                if (methodAnnotation.requiresCertification()) {
                    LOGGER.info("Requires certification: yes");
                }
                if (methodAnnotation.dependencies().length > 0) {
                    LOGGER.info("Dependencies: {}", Arrays.toString(methodAnnotation.dependencies()));
                }
                if (methodAnnotation.riskLevel() > 0) {
                    LOGGER.info("Risk level: {}", methodAnnotation.riskLevel());
                }
                LOGGER.info("------------------------");
            }
        }
    }
    private static final Map<String, BuildingOperationInfo> operationInfoCache = new ConcurrentHashMap<>();

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
        BuildingOperationInfo classInfo = getClassOperationInfo(clazz);
        if (classInfo != null) {
            operationInfoList.add(classInfo);
        }

        // Process method-level annotations
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(BuildingOperation.class)) {
                BuildingOperationInfo methodInfo = getMethodOperationInfo(clazz, method.getName());
                if (methodInfo != null) {
                    operationInfoList.add(methodInfo);
                }
            }
        }

        return operationInfoList;
    }

    /**
     * Gets BuildingOperation info for a specific method
     *
     * @param clazz      The class containing the method
     * @param methodName The name of the method
     * @return BuildingOperationInfo or null if not present
     */
    public static BuildingOperationInfo getMethodOperationInfo(Class<?> clazz, String methodName) {
        String cacheKey = clazz.getName() + "#" + methodName;

        // Check cache first
        if (operationInfoCache.containsKey(cacheKey)) {
            return operationInfoCache.get(cacheKey);
        }

        try {
            Method method = findMethodByName(clazz, methodName);
            if (method != null && method.isAnnotationPresent(BuildingOperation.class)) {
                BuildingOperation annotation = method.getAnnotation(BuildingOperation.class);
                BuildingOperationInfo info = new BuildingOperationInfo(
                        annotation.description(),
                        annotation.priority(),
                        annotation.estimatedCost(),
                        annotation.requiredTools(),
                        methodName,
                        clazz.getSimpleName()
                );
                operationInfoCache.put(cacheKey, info);
                return info;
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
     * @return BuildingOperationInfo or null if not present
     */
    public static BuildingOperationInfo getClassOperationInfo(Class<?> clazz) {
        String cacheKey = clazz.getName() + "#class";

        // Check cache first
        if (operationInfoCache.containsKey(cacheKey)) {
            return operationInfoCache.get(cacheKey);
        }

        if (clazz.isAnnotationPresent(BuildingOperation.class)) {
            BuildingOperation annotation = clazz.getAnnotation(BuildingOperation.class);
            BuildingOperationInfo info = new BuildingOperationInfo(
                    annotation.description(),
                    annotation.priority(),
                    annotation.estimatedCost(),
                    annotation.requiredTools(),
                    "",
                    clazz.getSimpleName()
            );
            operationInfoCache.put(cacheKey, info);
            return info;
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
        List<BuildingOperationInfo> highPriorityOps = new ArrayList<>();
        List<BuildingOperationInfo> allInfo = getOperationInfo(clazz);

        for (BuildingOperationInfo info : allInfo) {
            if (info.getPriority() == Priority.HIGH || info.getPriority() == Priority.CRITICAL) {
                highPriorityOps.add(info);
            }
        }

        return highPriorityOps;
    }

    /**
     * Gets operations with specified priority
     *
     * @param clazz    The class to analyze
     * @param priority Priority to filter by
     * @return List of BuildingOperationInfo objects with specified priority
     */
    public static List<BuildingOperationInfo> getOperationInfoByPriority(Class<?> clazz, Priority priority) {
        List<BuildingOperationInfo> filteredList = new ArrayList<>();
        List<BuildingOperationInfo> allInfo = getOperationInfo(clazz);

        for (BuildingOperationInfo info : allInfo) {
            if (info.getPriority() == priority) {
                filteredList.add(info);
            }
        }

        return filteredList;
    }

    /**
     * Checks if a specific method has BuildingOperation annotation
     *
     * @param clazz      The class containing the method
     * @param methodName The name of the method
     * @return true if method has annotation, false otherwise
     */
    public static boolean hasOperationAnnotation(Class<?> clazz, String methodName) {
        try {
            Method method = findMethodByName(clazz, methodName);
            return method != null && method.isAnnotationPresent(BuildingOperation.class);
        } catch (Exception e) {
            LOGGER.error("Error checking for operation annotation: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Clears the operation info cache
     */
    public static void clearCache() {
        operationInfoCache.clear();
        LOGGER.debug("BuildingOperationService cache cleared");
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
}
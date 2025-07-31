package com.solvd.buildingcompany.examples;

import com.solvd.buildingcompany.annotations.BuildingOperation;
import com.solvd.buildingcompany.annotations.BuildingOperationInfo;
import com.solvd.buildingcompany.annotations.Priority;
import com.solvd.buildingcompany.models.Project;
import com.solvd.buildingcompany.services.BuildingOperationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * Example class to demonstrate BuildingOperation annotation usage
 */
@BuildingOperation(
    description = "Example showing how to use BuildingOperation annotations",
    priority = Priority.LOW
)
public class AnnotationExample {
    private static final Logger LOGGER = LogManager.getLogger(AnnotationExample.class);

    @BuildingOperation(
        description = "Main method to run the annotation examples",
        requiredTools = {"java-runtime"}
    )
    public static void main(String[] args) {
        LOGGER.info("Starting annotation example with encapsulated service");

        // Process our own annotations with the encapsulated service
        List<BuildingOperationInfo> operations = BuildingOperationService.getOperationInfo(AnnotationExample.class);
        LOGGER.info("Found {} operations in AnnotationExample", operations.size());

        // Get class-level annotation info
        BuildingOperationInfo classInfo = BuildingOperationService.getClassOperationInfo(AnnotationExample.class);
        if (classInfo != null) {
            LOGGER.info("Class operation info: {}", classInfo.getDescription());
        }

        // Process Project class annotations
        BuildingOperationInfo projectClassInfo = BuildingOperationService.getClassOperationInfo(Project.class);
        if (projectClassInfo != null) {
            LOGGER.info("Project class operation info: {}", projectClassInfo.getDescription());
        }

        LOGGER.info("Annotation example completed");
    }

    @BuildingOperation(
        description = "Demonstrates a high priority foundation work",
        priority = Priority.CRITICAL,
        estimatedCost = 50000.0,
        requiredTools = {"concrete-mixer", "excavator", "rebar"}
    )
    public void buildFoundation() {
        LOGGER.info("Building foundation...");
        // Implementation would go here
    }

    @BuildingOperation(
        description = "Demonstrates a medium priority framing work",
        priority = Priority.HIGH,
        estimatedCost = 35000.0,
        requiredTools = {"nail-gun", "lumber", "level"}
    )
    public void buildFraming() {
        LOGGER.info("Building framing...");
        // Implementation would go here
    }

    @BuildingOperation(
        description = "Demonstrates a low priority finishing work",
        priority = Priority.MEDIUM,
        estimatedCost = 15000.0,
        requiredTools = {"paint", "brushes", "caulk"}
    )
    public void finishInterior() {
        LOGGER.info("Finishing interior...");
        // Implementation would go here
    }

    /**
     * Demonstrates how to use the encapsulated BuildingOperationInfo in code
     */
    public void demonstrateEncapsulatedInfo() {
        // Get information about our own methods
        BuildingOperationInfo foundationInfo = BuildingOperationService.getMethodOperationInfo(
                AnnotationExample.class, "buildFoundation");

        if (foundationInfo != null) {
            LOGGER.info("Planning foundation work:");
            LOGGER.info("Description: {}", foundationInfo.getDescription());
            LOGGER.info("Priority: {}", foundationInfo.getPriority());
            LOGGER.info("Budget: ${}", foundationInfo.getEstimatedCost());

            // Use the information in business logic
            if (foundationInfo.getPriority() == Priority.CRITICAL) {
                LOGGER.info("This is a CRITICAL priority task! Expediting resources...");
            }

            // Check if we have all required tools
            LOGGER.info("Checking required tools: {}", 
                    java.util.Arrays.toString(foundationInfo.getRequiredTools()));
        }
    }

    /**
     * Example of using the annotation info for advanced functionality
     */
    @BuildingOperation(
        description = "Calculates project budget based on annotations", 
        priority = Priority.HIGH
    )
    public double calculateProjectBudget() {
        // Get total cost from annotations
        double totalCost = BuildingOperationService.getTotalEstimatedCost(AnnotationExample.class);

        // Get high priority operations 
        List<BuildingOperationInfo> highPriorityOps = 
                BuildingOperationService.getHighPriorityOperations(AnnotationExample.class);

        // Add 20% contingency for high priority operations
        double contingency = 0.0;
        for (BuildingOperationInfo info : highPriorityOps) {
            contingency += info.getEstimatedCost() * 0.2;
        }

        LOGGER.info("Base budget: ${}", totalCost);
        LOGGER.info("Contingency: ${}", contingency);
        LOGGER.info("Total budget: ${}", totalCost + contingency);

        return totalCost + contingency;
    }
}

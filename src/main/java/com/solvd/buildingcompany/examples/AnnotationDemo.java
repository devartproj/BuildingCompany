package com.solvd.buildingcompany.examples;

import com.solvd.buildingcompany.annotations.BuildingOperationInfo;
import com.solvd.buildingcompany.models.Project;
import com.solvd.buildingcompany.services.BuildingOperationService;
import com.solvd.buildingcompany.utils.ReportGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * Simple demo class to show BuildingOperation annotations in logs
 */
public class AnnotationDemo {
    private static final Logger LOGGER = LogManager.getLogger(AnnotationDemo.class);

    public static void main(String[] args) {
        LOGGER.info("Starting annotation demonstration with encapsulation");

        // Process annotations using the service encapsulation
        LOGGER.info("\n=== Processing ReportGenerator annotations ===");
        BuildingOperationService.logOperationInfo(ReportGenerator.class);

        LOGGER.info("\n=== Processing Project annotations ===");
        BuildingOperationService.logOperationInfo(Project.class);

        LOGGER.info("\n=== Processing AnnotationExample annotations ===");
        BuildingOperationService.logOperationInfo(AnnotationExample.class);

        // Demonstrate more advanced encapsulated functionality
        LOGGER.info("\n=== Getting specific method information ===");
        BuildingOperationInfo methodInfo = BuildingOperationService.getMethodOperationInfo(
                AnnotationExample.class, "buildFoundation");
        if (methodInfo != null) {
            LOGGER.info("Found method info: {}", methodInfo);
        }

        LOGGER.info("\n=== Getting high priority operations ===");
        List<BuildingOperationInfo> highPriorityOps = 
                BuildingOperationService.getHighPriorityOperations(AnnotationExample.class);
        LOGGER.info("Found {} high priority operations", highPriorityOps.size());
        for (BuildingOperationInfo info : highPriorityOps) {
            LOGGER.info("High priority operation: {} (Priority: {})", 
                    info.getName(), info.getPriority());
        }

        LOGGER.info("\n=== Calculate total estimated cost ===");
        double totalCost = BuildingOperationService.getTotalEstimatedCost(AnnotationExample.class);
        LOGGER.info("Total estimated cost for AnnotationExample: ${}", totalCost);

        LOGGER.info("Annotation demonstration completed");
    }
}

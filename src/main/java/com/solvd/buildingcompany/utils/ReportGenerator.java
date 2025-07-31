package com.solvd.buildingcompany.utils;

import com.solvd.buildingcompany.annotations.BuildingOperation;
import com.solvd.buildingcompany.annotations.Priority;
import com.solvd.buildingcompany.models.ConstructionResult;
import com.solvd.buildingcompany.models.Project;
import com.solvd.buildingcompany.utils.BuildingOperationProcessor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Utility class for generating various reports
 */
@BuildingOperation(
    description = "Handles report generation for construction projects",
    priority = Priority.MEDIUM
)
public class ReportGenerator {
    private static final Logger LOGGER = LogManager.getLogger(ReportGenerator.class);

    private ReportGenerator() {
        // Private constructor to prevent instantiation
    }

            @BuildingOperation(
        description = "Generates a detailed report for a construction result",
        requiredTools = {"reporting-template", "project-data"}
            )
    public static String generateDetailedReport(ConstructionResult result) {
        LOGGER.debug("Generating detailed report for {}", result.project().getName());

        Project project = result.project();
        StringBuilder report = new StringBuilder();

        report.append("=== DETAILED CONSTRUCTION REPORT ===\n")
                .append("Project: ").append(project.getName()).append("\n")
                .append("Size: ").append(project.getArea()).append(" sqm, ")
                .append(project.getFloors()).append(" floors\n")
                .append("Material: ").append(project.getMaterial()).append("\n")
                .append("---\n")
                .append("Team size: ").append(result.team().getMembers().size()).append(" members\n")
                .append("Estimated cost: $").append(String.format("%.2f", result.estimatedCost())).append("\n")
                .append("Estimated duration: ").append(String.format("%.1f", result.estimatedCost())).append(" days\n")
                .append("=== END OF REPORT ===");

        return report.toString();
    }

    /**
     * Exports a report to a specified format
     *
     * @param result   The construction result
     * @param format   The export format ("pdf", "csv", etc.)
     * @param filename The output filename
     */
    @BuildingOperation(
        description = "Exports a construction report to a specified file format",
        priority = Priority.LOW,
        estimatedCost = 25.0,
        requiredTools = {"pdf-generator", "csv-converter", "file-system-access"}
    )
    public static void exportReport(ConstructionResult result, String format, String filename) {
        LOGGER.info("Exporting report to {} format: {}", format, filename);
        // Implementation would handle different export formats
    }
}

package com.solvd.buildingcompany.utils;

import com.solvd.buildingcompany.models.ConstructionResult;
import com.solvd.buildingcompany.models.Project;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Utility class for generating various reports
 */
public class ReportGenerator {
    private static final Logger LOGGER = LogManager.getLogger(ReportGenerator.class);

    private ReportGenerator() {
        // Private constructor to prevent instantiation
    }

    /**
     * Generates a detailed report for a construction result
     * 
     * @param result The construction result
     * @return A formatted report string
     */
    public static String generateDetailedReport(ConstructionResult result) {
        LOGGER.debug("Generating detailed report for {}", result.getProject().getName());

        Project project = result.getProject();
        StringBuilder report = new StringBuilder();

        report.append("=== DETAILED CONSTRUCTION REPORT ===\n")
              .append("Project: ").append(project.getName()).append("\n")
              .append("Size: ").append(project.getArea()).append(" sqm, ")
              .append(project.getFloors()).append(" floors\n")
              .append("Material: ").append(project.getMaterial()).append("\n")
              .append("---\n")
              .append("Team size: ").append(result.getTeam().getMembers().size()).append(" members\n")
              .append("Estimated cost: $").append(String.format("%.2f", result.getEstimatedCost())).append("\n")
              .append("Estimated duration: ").append(String.format("%.1f", result.getEstimatedDuration())).append(" days\n")
              .append("=== END OF REPORT ===");

        return report.toString();
    }

    /**
     * Exports a report to a specified format
     * 
     * @param result The construction result
     * @param format The export format ("pdf", "csv", etc.)
     * @param filename The output filename
     */
    public static void exportReport(ConstructionResult result, String format, String filename) {
        LOGGER.info("Exporting report to {} format: {}", format, filename);
        // Implementation would handle different export formats
    }
}

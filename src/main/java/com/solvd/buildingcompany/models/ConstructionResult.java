package com.solvd.buildingcompany.models;

import com.solvd.buildingcompany.interfaces.Reportable;
import com.solvd.buildingcompany.interfaces.TeamFilter;
import com.solvd.buildingcompany.models.workers.AbstractConstructionTeamMember;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.stream.Collectors;

public record ConstructionResult(
        Project project,
        ConstructionTeam<?> team,
        double estimatedCost,
        double estimatedDuration
) implements Reportable {
    private static final Logger LOGGER = LogManager.getLogger(ConstructionResult.class);

    public ConstructionResult {
        if (project == null) {
            throw new IllegalArgumentException("Project cannot be null");
        }
        if (team == null) {
            throw new IllegalArgumentException("Team cannot be null");
        }
        if (estimatedCost < 0) {
            throw new IllegalArgumentException("Estimated cost cannot be negative");
        }
        if (estimatedDuration < 0) {
            throw new IllegalArgumentException("Estimated duration cannot be negative");
        }
    }

    public <T extends AbstractConstructionTeamMember> long countTeamMembersByFilter(TeamFilter<T> filter) {
        return team.getMembers().stream()
                .filter(member -> filter.test((T) member))
                .count();
    }

    public <T extends AbstractConstructionTeamMember> List<String> getTeamMemberNamesByFilter(TeamFilter<T> filter) {
        return team.getMembers().stream()
                .filter(member -> filter.test((T) member))
                .map(AbstractConstructionTeamMember::getName)
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return String.format("Construction Result for %s:%n" +
                        "Team Size: %d%n" +
                        "Estimated Cost: $%.2f%n" +
                        "Estimated Duration: %.1f days",
                project.getName(),
                team.getSize(),
                estimatedCost,
                estimatedDuration);
    }

    @Override
    public String generateReport() {
        String report = String.format("Construction Report for %s\n" +
                        "Team size: %d members\n" +
                        "Estimated cost: $%.2f\n" +
                        "Estimated duration: %.1f days",
                project.getName(), team.getMembers().size(), estimatedCost, estimatedDuration);
        LOGGER.debug("Construction report generated");
        return report;
    }

    @Override
    public void exportToPDF(String filename) {
        LOGGER.info("Exporting construction results to {}", filename);
    }

}
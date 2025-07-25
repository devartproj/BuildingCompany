package com.solvd.buildingcompany.models;

import com.solvd.buildingcompany.interfaces.Reportable;
import com.solvd.buildingcompany.interfaces.TeamFilter;
import com.solvd.buildingcompany.models.workers.AbstractConstructionTeamMember;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.stream.Collectors;

public class ConstructionResult implements Reportable {
    private static final Logger LOGGER = LogManager.getLogger(ConstructionResult.class);

    private Project project;
    private ConstructionTeam team;
    private double estimatedCost;
    private double estimatedDuration;

    public ConstructionResult(Project project, ConstructionTeam team, double estimatedCost, double estimatedDuration) {
        this.project = project;
        this.team = team;
        this.estimatedCost = estimatedCost;
        this.estimatedDuration = estimatedDuration;
        LOGGER.debug("Construction result created for project: {}", project.getName());
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public ConstructionTeam getTeam() {
        return team;
    }

    public void setTeam(ConstructionTeam team) {
        this.team = team;
    }

    public double getEstimatedCost() {
        return estimatedCost;
    }

    public void setEstimatedCost(double estimatedCost) {
        this.estimatedCost = estimatedCost;
    }

    public double getEstimatedDuration() {
        return estimatedDuration;
    }

    public void setEstimatedDuration(double estimatedDuration) {
        this.estimatedDuration = estimatedDuration;
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

    @Override
    public String toString() {
        return String.format("ConstructionResult: %s (Cost: $%.2f, Duration: %.1f days)",
                project.getName(), estimatedCost, estimatedDuration);
    }

    /**
     * Counts team members that match a specified criteria
     * @param filter The filter criteria to apply
     * @return Count of matching team members
     */
    public <T extends AbstractConstructionTeamMember> long countTeamMembersByFilter(TeamFilter<T> filter) {
        @SuppressWarnings("unchecked")
        List<T> membersList = (List<T>) team.getMembers();
        return membersList.stream()
                .filter(filter::test)
                .count();
    }

    /**
     * Gets a list of team member names that match a specified criteria
     * @param filter The filter criteria to apply
     * @return List of team member names
     */
    public <T extends AbstractConstructionTeamMember> List<String> getTeamMemberNamesByFilter(TeamFilter<T> filter) {
        @SuppressWarnings("unchecked")
        List<T> membersList = (List<T>) team.getMembers();
        return membersList.stream()
                .filter(filter::test)
                .map(AbstractConstructionTeamMember::getName)
                .collect(Collectors.toList());
    }
}

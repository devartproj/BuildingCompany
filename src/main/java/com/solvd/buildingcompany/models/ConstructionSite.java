package com.solvd.buildingcompany.models;

import com.solvd.buildingcompany.exceptions.SafetyCheckFailedException;
import com.solvd.buildingcompany.interfaces.Inspectable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConstructionSite implements Inspectable {
    private static final Logger LOGGER = LogManager.getLogger(ConstructionSite.class);

    private Project project;
    private ConstructionTeam team;
    private String address;
    private boolean safetyCheckPassed;

    public ConstructionSite(Project project, ConstructionTeam team, String address) {
        this.project = project;
        this.team = team;
        this.address = address;
        this.safetyCheckPassed = false;
        LOGGER.debug("Construction site created at: {}", address);
    }

    public Project getProject() {
        return project;
    }

    public ConstructionTeam getTeam() {
        return team;
    }

    public String getAddress() {
        return address;
    }

    public boolean isSafetyCheckPassed() {
        return safetyCheckPassed;
    }

    public void setProject(Project project) {
        this.project = project;
        LOGGER.debug("Project updated for construction site at: {}", this.address);
    }

    public void setTeam(ConstructionTeam team) {
        this.team = team;
        LOGGER.debug("Construction team updated for site at: {}", this.address);
    }

    public void setAddress(String address) {
        this.address = address;
        LOGGER.debug("Address updated to: {}", address);
    }

    public void setSafetyCheckPassed(boolean safetyCheckPassed) {
        this.safetyCheckPassed = safetyCheckPassed;
        LOGGER.debug("Safety check status updated to: {}", safetyCheckPassed ? "passed" : "failed");
    }

    public void conductInspection() {
        try {
            boolean result = performSafetyCheck();
            if (result) {
                LOGGER.info("Site passed safety inspection");
            } else {
                throw new SafetyCheckFailedException("Safety measures need improvement", this.address);
            }
        } catch (SafetyCheckFailedException e) {
            LOGGER.error("Safety check failed at {}: {}", e.getSiteAddress(), e.getMessage());
            issueWarning(e.getMessage());
        }
    }

    @Override
    public boolean performSafetyCheck() {
        // In a real application, would have actual safety logic
        this.safetyCheckPassed = team.getMembers().size() >= 3;
        LOGGER.debug("Safety check: {}", safetyCheckPassed ? "passed" : "failed");
        return safetyCheckPassed;
    }

    @Override
    public void issueWarning(String message) {
        LOGGER.warn("WARNING: {}", message);
    }

    @Override
    public String getInspectionReport() {
        String status = safetyCheckPassed ? "Passed" : "Failed";
        String report = String.format("Inspection Report for site at %s\n" +
                        "Project: %s\n" +
                        "Safety Check Status: %s\n" +
                        "Team Size: %d members",
                address, project.getName(), status, team.getMembers().size());
        LOGGER.debug("Inspection report generated");
        return report;
    }

    @Override
    public String toString() {
        return String.format("ConstructionSite: %s (Project: %s, Safety: %s)",
                address, project.getName(), safetyCheckPassed ? "Passed" : "Not checked");
    }
}

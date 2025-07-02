package com.solvd.buildingcompany.models.workers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Plumber extends AbstractConstructionTeamMember {
    private static final Logger LOGGER = LogManager.getLogger(Plumber.class);

    private boolean hasOwnTools;

    public Plumber(String name, boolean hasOwnTools, double hourlyRate) {
        super(name, "Plumber", hourlyRate, 2);
        this.hasOwnTools = hasOwnTools;
        LOGGER.debug("Plumber created: {} (has own tools: {})", name, hasOwnTools);
    }

    public boolean getHasOwnTools() {
        return hasOwnTools;
    }

    public void setHasOwnTools(boolean hasOwnTools) {
        this.hasOwnTools = hasOwnTools;
    }

    public boolean hasOwnTools() {
        return hasOwnTools;
    }

    @Override
    public void performDuty() {
        LOGGER.info("Plumber {} installing plumbing system", getName());
    }

    @Override
    public boolean isCertified() {
        return true;
    }

    @Override
    public boolean canPerformComplexTask() {
        return hasOwnTools && getExperienceLevel() > 1;
    }

    @Override
    public double calculateMonthlySalary(int hoursWorked) {
        double toolBonus = hasOwnTools ? 0.15 : 0;
        double salary = getHourlyRate() * hoursWorked * (1 + toolBonus);
        LOGGER.debug("Plumber {} salary calculated: ${}", getName(), salary);
        return salary;
    }

    @Override
    public String toString() {
        return String.format("Plumber: %s (Has tools: %s)", getName(), hasOwnTools);
    }
}
package com.solvd.buildingcompany.models.workers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Builder extends AbstractConstructionTeamMember {
    private static final Logger LOGGER = LogManager.getLogger(Builder.class);

    private String specialization;

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public Builder(String name, String specialization, double hourlyRate) {
        super(name, "Builder", hourlyRate, 3);
        this.specialization = specialization;
        LOGGER.debug("Builder created: {} (specialization: {})", name, specialization);
    }

    public String getSpecialization() {
        return specialization;
    }

    @Override
    public void performDuty() {
        LOGGER.info("Builder {} performing {} work", getName(), specialization);
    }

    @Override
    public boolean isCertified() {
        return true;
    }

    @Override
    public boolean canPerformComplexTask() {
        return getExperienceLevel() >= 3;
    }

    @Override
    public double calculateMonthlySalary(int hoursWorked) {
        double salary = getHourlyRate() * hoursWorked * (1 + getExperienceLevel() * 0.1);
        LOGGER.debug("Builder {} salary calculated: ${}", getName(), salary);
        return salary;
    }

    @Override
    public String toString() {
        return String.format("Builder: %s (%s)", getName(), specialization);
    }
}

package com.solvd.buildingcompany.models.workers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Electrician extends AbstractConstructionTeamMember {
    private static final Logger LOGGER = LogManager.getLogger(Electrician.class);

    private double insuranceAmount;

    public Electrician(String name, double insuranceAmount, double hourlyRate) {
        super(name, "Electrician", hourlyRate, 4);
        this.insuranceAmount = insuranceAmount;
        LOGGER.debug("Electrician created: {} (insurance: ${})", name, insuranceAmount);
    }

    public double getInsuranceAmount() {
        return insuranceAmount;
    }

    public void setInsuranceAmount(double insuranceAmount) {
        this.insuranceAmount = insuranceAmount;
    }

    @Override
    public void performDuty() {
        LOGGER.info("Electrician {} working on electrical wiring", getName());
    }

    @Override
    public boolean isCertified() {
        return insuranceAmount >= 1000;
    }

    @Override
    public boolean canPerformComplexTask() {
        return isCertified() && getExperienceLevel() > 3;
    }

    @Override
    public double calculateMonthlySalary(int hoursWorked) {
        double hazardPay = 0.2; // 20% extra for dangerous work
        double salary = getHourlyRate() * hoursWorked * (1 + hazardPay);
        LOGGER.debug("Electrician {} salary calculated: ${}", getName(), salary);
        return salary;
    }

    @Override
    public String toString() {
        return String.format("Electrician: %s (Insurance: $%.2f)", getName(), insuranceAmount);
    }
}
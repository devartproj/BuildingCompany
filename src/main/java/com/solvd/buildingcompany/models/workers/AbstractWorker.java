package com.solvd.buildingcompany.models.workers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class AbstractWorker {
    private static final Logger LOGGER = LogManager.getLogger(AbstractWorker.class);

    private String name;
    private String position;
    private double hourlyRate;

    public AbstractWorker(String name, String position, double hourlyRate) {
        this.name = name;
        this.position = position;
        this.hourlyRate = hourlyRate;
        LOGGER.debug("Worker created: {} ({})", name, position);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public double getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public abstract double calculateMonthlySalary(int hoursWorked);
}

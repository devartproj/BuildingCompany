package com.solvd.buildingcompany.models;

import com.solvd.buildingcompany.exceptions.InvalidMaterialException;
import com.solvd.buildingcompany.exceptions.ProjectSizeTooLargeException;
import com.solvd.buildingcompany.interfaces.Buildable;
import com.solvd.buildingcompany.interfaces.Reportable;
import com.solvd.buildingcompany.services.ConstructionCalculator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Project implements Buildable, Reportable {
    private static final Logger LOGGER = LogManager.getLogger(Project.class);

    private String name;
    private double area;
    private int floors;
    private String material;

    public Project(String name, double area, int floors, String material) throws ProjectSizeTooLargeException {
        this.name = name;

        // Validate project size (maximum 1000 sqm per floor)
        double maxAllowedSize = 1000.0;
        if (area > maxAllowedSize) {
            throw new ProjectSizeTooLargeException("Project area exceeds maximum allowed size", area, maxAllowedSize);
        }

        this.area = area;
        this.floors = floors;
        this.material = material;
        LOGGER.debug("Project created: {}", name);
    }

    public String getName() {
        return name;
    }

    public double getArea() {
        return area;
    }

    public int getFloors() {
        return floors;
    }

    public String getMaterial() {
        return material;
    }

    public void setName(String name) {
        this.name = name;
        LOGGER.debug("Project name updated to: {}", name);
    }

    public void setArea(double area) throws ProjectSizeTooLargeException {
        double maxAllowedSize = 1000.0;
        if (area > maxAllowedSize) {
            throw new ProjectSizeTooLargeException("Project area exceeds maximum allowed size", area, maxAllowedSize);
        }
        this.area = area;
        LOGGER.debug("Project area updated to: {} sqm", area);
    }

    public void setFloors(int floors) {
        this.floors = floors;
        LOGGER.debug("Project floors updated to: {}", floors);
    }

    public void setMaterial(String material) {
        this.material = material;
        LOGGER.debug("Project material updated to: {}", material);
    }

    @Override
    public double calculateConstructionTime() {
        double time = this.area * this.floors * 0.5;
        LOGGER.debug("Construction time estimated: {} days", time);
        return time;
    }

    @Override
    public double estimateCost(ConstructionTeam team) throws InvalidMaterialException {
        if (team == null) {
            throw new IllegalArgumentException("Construction team cannot be null");
        }

        ConstructionCalculator calculator = new ConstructionCalculator();

        // Расчет стоимости материалов через новый метод
        double materialCost = calculator.calculateMaterialCost(this);
        // Расчет стоимости работы
        double laborCost = calculator.calculateLaborCost(this, team);

        double totalCost = materialCost + laborCost;
        LOGGER.debug("Project cost estimated: ${}", totalCost);
        return totalCost;
    }

    @Override
    public boolean validateBlueprint() {
        boolean isValid = this.area > 0 && this.floors > 0 && !this.material.isEmpty();
        LOGGER.debug("Blueprint validation: {}", isValid ? "passed" : "failed");
        return isValid;
    }

    @Override
    public String generateReport() {
        String report = String.format("Project: %s\nArea: %.2f\nFloors: %d\nMaterial: %s",
                name, area, floors, material);
        LOGGER.debug("Project report generated");
        return report;
    }

    @Override
    public void exportToPDF(String filename) {
        LOGGER.info("Exporting project to {}", filename);
    }

    @Override
    public String toString() {
        return String.format("Project: %s (%.2f m², %d floors, %s)",
                name, area, floors, material);
    }
}

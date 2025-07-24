package com.solvd.buildingcompany.services;

import com.solvd.buildingcompany.exceptions.InvalidMaterialException;
import com.solvd.buildingcompany.exceptions.InsufficientTeamSizeException;
import com.solvd.buildingcompany.exceptions.BudgetExceededException;
import com.solvd.buildingcompany.interfaces.CostEstimator;
import com.solvd.buildingcompany.interfaces.MaterialProcessor;
import com.solvd.buildingcompany.models.ConstructionResult;
import com.solvd.buildingcompany.models.ConstructionTeam;
import com.solvd.buildingcompany.models.Project;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConstructionCalculator {
    private static final Logger LOGGER = LogManager.getLogger(ConstructionCalculator.class);

    private static final double BRICK_COST = 5000.0;
    private static final double WOOD_COST = 3000.0;
    private static final double CONCRETE_COST = 4000.0;
    private static final double DEFAULT_COST = 4500.0;

    public ConstructionResult calculate(Project project, ConstructionTeam team) throws InvalidMaterialException, InsufficientTeamSizeException, BudgetExceededException {
        LOGGER.info("Starting calculation for project: {}", project.getName());

        int teamSize = team.getMembers().size();
        if (teamSize < 3) {
            throw new InsufficientTeamSizeException("Team is too small for construction project", teamSize, 3);
        }

        // Using CostEstimator functional interface
        CostEstimator materialCostEstimator = (area, coefficient) -> area * coefficient * project.getFloors();

        // Get coefficient based on material
        double coefficient = getMaterialCoefficient(project.getMaterial());
        double materialCost = materialCostEstimator.calculateEstimatedCost(project.getArea(), coefficient);
        double laborCost = calculateLaborCost(project, team);
        double totalCost = materialCost + laborCost;

        double availableBudget = 100000000.0;
        if (totalCost > availableBudget) {
            throw new BudgetExceededException("Project cost exceeds available budget", totalCost, availableBudget);
        }

        double duration = calculateDuration(project, team);

        LOGGER.debug("Calculation completed. Cost: ${}, Duration: {} days", totalCost, duration);
        return new ConstructionResult(project, team, totalCost, duration);
    }

    private double getMaterialCoefficient(String material) throws InvalidMaterialException {
        MaterialProcessor materialProcessor = mat -> mat.toLowerCase();
        String processedMaterial = materialProcessor.processMaterial(material);

        switch (processedMaterial) {
            case "brick":
                return BRICK_COST;
            case "wood":
                return WOOD_COST;
            case "concrete":
                return CONCRETE_COST;
            default:
                throw new InvalidMaterialException("Unsupported building material: " + material);
        }
    }

    public double calculateMaterialCost(Project project) throws InvalidMaterialException {
        // Using CostEstimator functional interface
        CostEstimator materialCostEstimator = (area, coefficient) -> area * coefficient * project.getFloors();

        // Get coefficient based on material
        double coefficient = getMaterialCoefficient(project.getMaterial());
        double materialCost = materialCostEstimator.calculateEstimatedCost(project.getArea(), coefficient);

        LOGGER.debug("Material cost calculated: ${}", materialCost);
        return materialCost;
    }

    public double calculateLaborCost(Project project, ConstructionTeam team) {
        double area = project.getArea();
        int teamSize = team.getMembers().size();

        final double AREA_FACTOR = 2000.0;
        final double TEAM_MEMBER_COST = 50000.0;

        double cost = area * AREA_FACTOR + teamSize * TEAM_MEMBER_COST;
        LOGGER.debug("Labor cost calculated: ${}", cost);
        return cost;
    }

    private double calculateDuration(Project project, ConstructionTeam team) {
        double area = project.getArea();
        int floors = project.getFloors();
        int teamSize = team.getMembers().size();

        final double AREA_DIVISOR = 10.0;
        final double TEAM_EFFICIENCY_FACTOR = 0.02;

        double baseTime = area / AREA_DIVISOR * floors;
        double teamFactor = 1.0 - (teamSize * TEAM_EFFICIENCY_FACTOR);
        double duration = Math.ceil(baseTime * teamFactor);
        LOGGER.debug("Project duration calculated: {} days", duration);
        return duration;
    }
}

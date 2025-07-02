package com.solvd.buildingcompany;

import com.solvd.buildingcompany.exceptions.BudgetExceededException;
import com.solvd.buildingcompany.exceptions.InsufficientTeamSizeException;
import com.solvd.buildingcompany.exceptions.InvalidMaterialException;
import com.solvd.buildingcompany.exceptions.ProjectSizeTooLargeException;
import com.solvd.buildingcompany.models.Project;
import com.solvd.buildingcompany.models.BuildingMaterial;
import com.solvd.buildingcompany.services.ConstructionCalculator;
import com.solvd.buildingcompany.models.ConstructionResult;
import com.solvd.buildingcompany.models.ConstructionSite;
import com.solvd.buildingcompany.models.ConstructionTeam;
import com.solvd.buildingcompany.models.workers.Builder;
import com.solvd.buildingcompany.models.workers.Electrician;
import com.solvd.buildingcompany.models.workers.Plumber;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
    private static final Logger LOGGER = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        LOGGER.info("Starting BuildingCompany application");

        try {
            // Create a project with validation
            Project houseProject = new Project("Residential House", 150, 2, "brick");
            LOGGER.debug("Created project: {}", houseProject);

            // Form a construction team
            ConstructionTeam team = new ConstructionTeam();
            team.addMember(new Plumber("John Smith", true, 45));
            team.addMember(new Electrician("Mike Johnson", 1000, 50));
            team.addMember(new Builder("David Wilson", "mason", 40));
            LOGGER.debug("Construction team formed: {}", team.getMembers());

            try {
                // Calculate project costs and timeline
                ConstructionCalculator calculator = new ConstructionCalculator();
                ConstructionResult result = calculator.calculate(houseProject, team);
                LOGGER.info("Project calculation completed");

                LOGGER.info("=== Project ===");
                LOGGER.info(houseProject);
                LOGGER.info("=== Team ===");
                LOGGER.info(team.getMembers());
                LOGGER.info("=== Result ===");
                LOGGER.info(result);

                BuildingMaterial bricks = new BuildingMaterial("Bricks", 0.5, "piece");
                LOGGER.info("Material cost: " + bricks.calculateCost());

                // Create construction site and conduct inspection
                ConstructionSite site = new ConstructionSite(houseProject, team, "123 Main St");
                site.conductInspection();
                LOGGER.info("Site inspection conducted");

                LOGGER.info("=== Workers ===");
                team.getMembers().forEach(worker -> {
                    LOGGER.info(worker + " | Salary: " + worker.calculateMonthlySalary(160));
                });
            } catch (InvalidMaterialException e) {
                LOGGER.error("Invalid material specified: {}", e.getMessage());
            } catch (InsufficientTeamSizeException e) {
                LOGGER.error("Team size insufficient. Current: {}, Required: {}", 
                        e.getActualSize(), e.getRequiredSize());
            } catch (BudgetExceededException e) {
                LOGGER.error("Budget exceeded by ${}", e.getExceededAmount());
                LOGGER.error("Available: ${}, Required: ${}", 
                        e.getAvailableBudget(), e.getCalculatedCost());
            }
        } catch (ProjectSizeTooLargeException e) {
            LOGGER.error("Project size too large: {}sqm (maximum: {}sqm)", 
                    e.getActualSize(), e.getMaxAllowedSize());
        }

        LOGGER.info("Application execution completed");
    }
}

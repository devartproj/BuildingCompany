package com.solvd.buildingcompany;

import com.solvd.buildingcompany.collections.CustomLinkedList;
import com.solvd.buildingcompany.exceptions.BudgetExceededException;
import com.solvd.buildingcompany.exceptions.InsufficientTeamSizeException;
import com.solvd.buildingcompany.exceptions.InvalidMaterialException;
import com.solvd.buildingcompany.exceptions.ProjectSizeTooLargeException;
import com.solvd.buildingcompany.models.*;
import com.solvd.buildingcompany.models.workers.AbstractConstructionTeamMember;
import com.solvd.buildingcompany.models.workers.Builder;
import com.solvd.buildingcompany.models.workers.Electrician;
import com.solvd.buildingcompany.models.workers.Plumber;
import com.solvd.buildingcompany.services.ConstructionCalculator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.util.*;

public class Main {
    private static final Logger LOGGER = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        LOGGER.info("Starting BuildingCompany application");

        try {
            // Create a project with validation
            Project houseProject = new Project("Residential House", 150, 2, "brick");
            Project officeProject = new Project("Office Building", 300, 3, "concrete");
            Project garageProject = new Project("Garage", 50, 1, "wood");
            LOGGER.debug("Created projects");

            // Form a generic construction team
            ConstructionTeam<AbstractConstructionTeamMember> team = new ConstructionTeam<>();
            team.addMember(new Plumber("John Smith", true, 45));
            team.addMember(new Electrician("Mike Johnson", 1000, 50));
            team.addMember(new Builder("David Wilson", "mason", 40));
            team.addMember(new Plumber("Robert Brown", false, 30));
            team.addMember(new Builder("Daniel Moore", "carpenter", 35));
            LOGGER.debug("Construction team formed: {}", team);

            // Demonstrate custom LinkedList
            LOGGER.info("=== Custom LinkedList Demo ===");
            CustomLinkedList<String> taskList = new CustomLinkedList<>();
            taskList.add("Prepare site");
            taskList.add("Lay foundation");
            taskList.add("Build walls");
            taskList.add("Install roof");
            taskList.add("Interior work");
            LOGGER.info("Task list: {}", taskList);

            LOGGER.info("Task at index 2: {}", taskList.get(2));
            taskList.remove(3);
            LOGGER.info("After removing task 3: {}", taskList);
            taskList.add(1, "Delivery materials");
            LOGGER.info("After adding task at index 1: {}", taskList);

            // Demonstrate MaterialInventory with generics
            LOGGER.info("\n=== Material Inventory Demo ===");
            MaterialInventory<BuildingMaterial> inventory = new MaterialInventory<>();

            BuildingMaterial bricks = new BuildingMaterial("Bricks", 0.5, "piece");
            BuildingMaterial cement = new BuildingMaterial("Cement", 10.0, "bag");
            BuildingMaterial lumber = new BuildingMaterial("Lumber", 15.0, "board");
            BuildingMaterial paint = new BuildingMaterial("Paint", 25.0, "gallon");

            inventory.addMaterial(bricks);
            inventory.addMaterial(cement);
            inventory.addMaterial(lumber);
            inventory.addMaterial(paint);

            inventory.addSupplier("ABC Materials");
            inventory.addSupplier("XYZ Building Supply");
            inventory.addSupplier("123 Hardware");

            LOGGER.info("Total inventory count: {}", inventory.getTotalInventoryCount());
            LOGGER.info("Total inventory value: ${}", inventory.calculateTotalInventoryValue());
            LOGGER.info("Suppliers: {}", inventory.getSuppliers());

            inventory.orderMaterial(cement);
            inventory.orderMaterial(paint);
            BuildingMaterial processedOrder = inventory.processPendingOrder();
            LOGGER.info("Processed order: {}", processedOrder.getName());

            // Demonstrate ProjectSchedule with generics
            LOGGER.info("\n=== Project Schedule Demo ===");
            ProjectSchedule<Project> schedule = new ProjectSchedule<>();

            LocalDate today = LocalDate.now();
            schedule.scheduleProject(today, houseProject);
            schedule.scheduleProject(today.plusMonths(1), officeProject);
            schedule.scheduleProject(today.plusMonths(3), garageProject);

            LOGGER.info("Total scheduled projects: {}", schedule.getScheduledProjectCount());
            LOGGER.info("Next project: {}", schedule.getNextProject(today).getName());
            LOGGER.info("Project in 2 months: {}",
                    schedule.getNextProject(today.plusMonths(2)).getName());

            try {
                // Calculate project costs and timeline using the generic team
                ConstructionCalculator calculator = new ConstructionCalculator();
                ConstructionResult result = calculator.calculate(houseProject, team);
                LOGGER.info("\n=== Project Calculation ===");
                LOGGER.info("Project: {}", houseProject);
                LOGGER.info("Team size: {}", team.getSize());
                LOGGER.info("Unique specializations: {}", team.getUniqueSpecializations());
                LOGGER.info("Team lead: {}", team.getTeamLead().getName());
                LOGGER.info("Builders in team: {}", team.getMembersByPosition("Builder").size());
                LOGGER.info("Calculation result: {}", result);

                // Create construction site and conduct inspection
                ConstructionSite site = new ConstructionSite(houseProject, team, "123 Main St");
                site.conductInspection();
                LOGGER.info("Site inspection conducted");
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

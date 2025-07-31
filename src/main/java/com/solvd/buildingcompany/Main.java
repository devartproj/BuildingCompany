package com.solvd.buildingcompany;

import com.solvd.buildingcompany.annotations.BuildingOperation;
import com.solvd.buildingcompany.annotations.BuildingOperationInfo;
import com.solvd.buildingcompany.collections.CustomLinkedList;
import com.solvd.buildingcompany.enums.BuildingType;
import com.solvd.buildingcompany.enums.ConstructionPhase;
import com.solvd.buildingcompany.enums.MaterialType;
import com.solvd.buildingcompany.exceptions.BudgetExceededException;
import com.solvd.buildingcompany.exceptions.InsufficientTeamSizeException;
import com.solvd.buildingcompany.exceptions.InvalidMaterialException;
import com.solvd.buildingcompany.exceptions.ProjectSizeTooLargeException;
import com.solvd.buildingcompany.interfaces.CostEstimator;
import com.solvd.buildingcompany.interfaces.MaterialProcessor;
import com.solvd.buildingcompany.interfaces.TeamFilter;
import com.solvd.buildingcompany.models.*;
import com.solvd.buildingcompany.models.workers.AbstractConstructionTeamMember;
import com.solvd.buildingcompany.models.workers.Builder;
import com.solvd.buildingcompany.models.workers.Electrician;
import com.solvd.buildingcompany.models.workers.Plumber;
import com.solvd.buildingcompany.services.BuildingOperationService;
import com.solvd.buildingcompany.services.ConstructionCalculator;
import com.solvd.buildingcompany.utils.MessageLogger;
import com.solvd.buildingcompany.utils.MessageType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    private static final Logger LOGGER = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        try {
            System.out.println("========================================================");
            System.out.println("          BUILDING COMPANY APPLICATION STARTING");
            System.out.println("========================================================");

            try {
                // Create a project with validation
                Project houseProject = new Project("Residential House", 150, 2, "brick");
                Project officeProject = new Project("Office Building", 300, 3, "concrete");
                Project garageProject = new Project("Garage", 50, 1, "wood");
                LOGGER.debug("Created projects");
                System.out.println("Created projects: " + houseProject.getName() + ", " + officeProject.getName() + ", " + garageProject.getName());

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
                LOGGER.info("Processed order: {}", processedOrder.name());

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

                System.out.println("\n=== Project Schedule Information ===");
                System.out.println("Total scheduled projects: " + schedule.getScheduledProjectCount());
                System.out.println("Next project: " + schedule.getNextProject(today).getName());
                System.out.println("Project in 2 months: " + schedule.getNextProject(today.plusMonths(2)).getName());

                // Using stream to filter projects
                System.out.println("\n=== Using Stream Operations ===");
                List<Project> allProjects = new ArrayList<>();
                allProjects.add(houseProject);
                allProjects.add(officeProject);
                allProjects.add(garageProject);

                System.out.println("Projects with area greater than 100 sqm:");
                allProjects.stream()
                        .filter(p -> p.getArea() > 100)
                        .map(Project::getName)
                        .forEach(name -> System.out.println("- " + name));

                // Using functional interfaces
                System.out.println("\n=== Using Functional Interfaces ===");
                CostEstimator simpleEstimator = (area, coef) -> area * coef;
                System.out.println("Simple cost estimate for house: $" +
                        simpleEstimator.calculateEstimatedCost(houseProject.getArea(), 1000));

                MaterialProcessor uppercaseProcessor = material -> material.toUpperCase();
                System.out.println("Processed materials:");
                allProjects.stream()
                        .map(Project::getMaterial)
                        .map(uppercaseProcessor::processMaterial)
                        .distinct()
                        .forEach(System.out::println);

                try {
                    // Calculate project costs and timeline using the generic team
                    ConstructionCalculator calculator = new ConstructionCalculator();
                    ConstructionResult result = calculator.calculate(houseProject, team);
                    LOGGER.info("\n=== Project Calculation ===");
                    LOGGER.info("Project: {}", houseProject);
                    LOGGER.info("Team size: {}", team.getSize());
                    LOGGER.info("Unique specializations: {}", team.getUniqueSpecializations());
                    LOGGER.info("Team lead: {}", team.getTeamLead().getName());

                    // Using streams instead of direct size method
                    long builderCount = team.getMembers().stream()
                            .filter(member -> "Builder".equals(member.getPosition()))
                            .count();
                    LOGGER.info("Builders in team: {}", builderCount);
                    LOGGER.info("Calculation result: {}", result);

                    // Using TeamFilter functional interface
                    System.out.println("\n=== Using TeamFilter Functional Interface ===");
                    // Filter for experienced team members (experience > 3)
                    TeamFilter<AbstractConstructionTeamMember> experiencedFilter =
                            member -> member.getExperienceLevel() > 3;
                    long experiencedCount = result.countTeamMembersByFilter(experiencedFilter);
                    System.out.println("Experienced team members: " + experiencedCount);

                    // Filter for plumbers
                    TeamFilter<AbstractConstructionTeamMember> plumberFilter =
                            member -> "Plumber".equals(member.getPosition());
                    List<String> plumberNames = result.getTeamMemberNamesByFilter(plumberFilter);
                    System.out.println("Plumbers in team: " + String.join(", ", plumberNames));

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

            // Демонстрация использования MessageLogger
            System.out.println("\n=== Демонстрация MessageLogger (Singleton pattern) ===");
            System.out.println("Запуск демонстрации работы с сообщениями в многопоточной среде...");

            // Получаем экземпляр MessageLogger (Singleton)
            MessageLogger logger = MessageLogger.getInstance();
            logger.logMessage("Main", MessageType.INFO, "Демонстрация работы с потокобезопасным логгером");

            // Create and start several threads for demonstration
            ExecutorService demoExecutor = Executors.newFixedThreadPool(3);
            CountDownLatch demoLatch = new CountDownLatch(3);

            for (int i = 0; i < 3; i++) {
                final int threadNum = i + 1;
                demoExecutor.submit(() -> {
                    try {
                        String threadName = "DemoThread-" + threadNum;
                        // Log different types of messages
                        logger.logMessage(threadName, MessageType.INFO,
                                "Message from demonstration thread " + threadNum);
                        Thread.sleep(100);

                        if (threadNum == 2) {
                            logger.logWarning(threadName, "This is a warning from thread " + threadNum);
                        } else if (threadNum == 3) {
                            logger.logError(threadName, "This is an error message from thread " + threadNum);
                        }
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    } finally {
                        demoLatch.countDown();
                    }
                });
            }

            try {
                demoLatch.await(2, TimeUnit.SECONDS);
                System.out.println("Total messages sent: " + logger.getMessageCount());
                System.out.println("For complete demonstration run these classes:");
                System.out.println("- com.solvd.buildingcompany.examples.MessageLoggerDemo");
                System.out.println("- com.solvd.buildingcompany.examples.ThreadSafeConstruction\n");

                // BuildingOperationService demonstration
                System.out.println("=== BuildingOperationService Demonstration ====");
                List<BuildingOperationInfo> highPriorityOps = BuildingOperationService.getHighPriorityOperations(Project.class);
                System.out.println("High priority operations in Project:");
                for (BuildingOperationInfo info : highPriorityOps) {
                    System.out.println(" - " + info.getMethodName() + ": " + info.getDescription());
                }

                // Annotation caching demonstration
                long startTime = System.nanoTime();
                BuildingOperationInfo info1 = BuildingOperationService.getMethodOperationInfo(Project.class, "estimateCost");
                long firstCallTime = System.nanoTime() - startTime;

                startTime = System.nanoTime();
                BuildingOperationInfo info2 = BuildingOperationService.getMethodOperationInfo(Project.class, "estimateCost");
                long secondCallTime = System.nanoTime() - startTime;

                System.out.println("\nAnnotation caching demonstration:");
                System.out.println("First call time: " + firstCallTime + " ns");
                System.out.println("Second call time: " + secondCallTime + " ns");
                System.out.println("Speed-up: " + (firstCallTime / (double) secondCallTime) + "x\n");

                demoExecutor.shutdown();
                if (!demoExecutor.awaitTermination(2, TimeUnit.SECONDS)) {
                    demoExecutor.shutdownNow();
                }
            } catch (InterruptedException e) {
                demoExecutor.shutdownNow();
                Thread.currentThread().interrupt();
            }

            LOGGER.info("Application execution completed");
            System.out.println("\n========================================================");
            System.out.println("          BUILDING COMPANY APPLICATION COMPLETED");
            System.out.println("========================================================");
        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

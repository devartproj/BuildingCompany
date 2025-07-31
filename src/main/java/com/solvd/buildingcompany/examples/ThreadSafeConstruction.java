package com.solvd.buildingcompany.examples;

import com.solvd.buildingcompany.annotations.BuildingOperation;
import com.solvd.buildingcompany.annotations.Priority;
import com.solvd.buildingcompany.exceptions.ProjectSizeTooLargeException;
import com.solvd.buildingcompany.models.ConstructionTeam;
import com.solvd.buildingcompany.models.Project;
import com.solvd.buildingcompany.models.workers.AbstractConstructionTeamMember;
import com.solvd.buildingcompany.models.workers.Builder;
import com.solvd.buildingcompany.models.workers.Electrician;
import com.solvd.buildingcompany.models.workers.Plumber;
import com.solvd.buildingcompany.utils.MessageLogger;
import com.solvd.buildingcompany.utils.MessageType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Demonstration of MessageLogger usage in real construction project scenarios
 */
@BuildingOperation(
    description = "Demonstrates threaded construction activities with message logging",
    priority = Priority.HIGH
)
public class ThreadSafeConstruction {
    private static final Logger LOGGER = LogManager.getLogger(ThreadSafeConstruction.class);
    private static final int PROJECT_COUNT = 3;

    public static void main(String[] args) {
        try {
            System.out.println("\n========================================================");
            System.out.println("      THREAD-SAFE CONSTRUCTION SIMULATION STARTING");
            System.out.println("========================================================\n");

            // Create projects
            List<Project> projects = new ArrayList<>();
            projects.add(new Project("Residential Building", 200, 3, "brick"));
            projects.add(new Project("Office Building", 350, 5, "concrete"));
            projects.add(new Project("Warehouse", 500, 1, "metal"));

            // Create construction teams
            List<ConstructionTeam<AbstractConstructionTeamMember>> teams = new ArrayList<>();
            for (int i = 0; i < PROJECT_COUNT; i++) {
                ConstructionTeam<AbstractConstructionTeamMember> team = new ConstructionTeam<>();
                team.addMember(new Builder("Builder-" + (i*3+1), "mason", 45));
                team.addMember(new Plumber("Plumber-" + (i*3+2), (i % 2 == 0), 40));
                team.addMember(new Electrician("Electrician-" + (i*3+3), 1000, 50));
                teams.add(team);
            }

            // Create thread pool for construction work simulation
            ExecutorService executor = Executors.newFixedThreadPool(PROJECT_COUNT);
            CountDownLatch latch = new CountDownLatch(PROJECT_COUNT);
            final CyclicBarrier barrier = new CyclicBarrier(PROJECT_COUNT, () -> {
                MessageLogger.getInstance().logMessage("Coordinator", MessageType.SYSTEM, 
                    "All teams reached checkpoint! Moving to next phase.");
            });

            // Start construction of each project in a separate thread
            for (int i = 0; i < PROJECT_COUNT; i++) {
                final Project project = projects.get(i);
                final ConstructionTeam<AbstractConstructionTeamMember> team = teams.get(i);
                final int projectNum = i + 1;

                executor.submit(() -> {
                    try {
                        Thread.currentThread().setName("Project-" + projectNum);
                        String threadName = Thread.currentThread().getName();
                        MessageLogger logger = MessageLogger.getInstance();

                        // Phase 1: Project preparation
                        logger.logMessage(threadName, MessageType.INFO, 
                            "Starting work on project '" + project.getName() + "'");
                        Thread.sleep(500);

                        // Log team information
                        logger.logMessage(threadName, MessageType.INFO, 
                            "Team formed, member count: " + team.getSize());

                        // Log detailed project information
                        logger.logMessage(threadName, MessageType.INFO, 
                            String.format("Project details: area %.2f sqm, %d floors, material: %s", 
                            project.getArea(), project.getFloors(), project.getMaterial()));
                        Thread.sleep(300);

                        // Synchronization: wait for all threads to reach this point
                        logger.logMessage(threadName, MessageType.SYSTEM, 
                            "Preparation complete, waiting for other teams");
                        barrier.await(5, TimeUnit.SECONDS);

                        // Phase 2: Foundation construction
                        logger.logMessage(threadName, MessageType.INFO, 
                            "Starting foundation work");
                        Thread.sleep(1000);

                        // Randomly generate warning for some projects
                        if (Math.random() > 0.7) {
                            logger.logWarning(threadName, 
                                "Groundwater level issue detected");
                            Thread.sleep(200);
                        }

                        // Synchronization: wait for all threads to reach this point
                        logger.logMessage(threadName, MessageType.SYSTEM, 
                            "Foundation complete, waiting for other teams");
                        barrier.await(5, TimeUnit.SECONDS);

                        // Phase 3: Wall construction
                        logger.logMessage(threadName, MessageType.INFO, 
                            "Starting wall construction");
                        Thread.sleep(800);

                        // Generate error for one specific project
                        if (projectNum == 2) {
                            logger.logError(threadName, 
                                "Deviation from plans detected! Correction required");
                            Thread.sleep(500);
                            logger.logMessage(threadName, MessageType.INFO, 
                                "Issue resolved, continuing construction");
                        }

                        logger.logMessage(threadName, MessageType.INFO, 
                            "Project '" + project.getName() + "' successfully completed!");

                    } catch (InterruptedException | BrokenBarrierException e) {
                        MessageLogger.getInstance().logError(Thread.currentThread().getName(), 
                            "Error during construction: " + e.getMessage());
                        Thread.currentThread().interrupt();
                    } catch (TimeoutException e) {
                        MessageLogger.getInstance().logError(Thread.currentThread().getName(), 
                            "Timeout exceeded: " + e.getMessage());
                    } finally {
                        latch.countDown();
                    }
                });
            }

            // wait for all threads to complete
            latch.await(15, TimeUnit.SECONDS);

            // Выводим итоговую статистику
            MessageLogger logger = MessageLogger.getInstance();
            System.out.println("\n========================================================");
            System.out.println("      THREAD-SAFE CONSTRUCTION SIMULATION COMPLETED");
            System.out.println("      Total messages logged: " + logger.getMessageCount());
            System.out.println("========================================================\n");

            // Complete logging
            executor.shutdown();
            if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }

        } catch (ProjectSizeTooLargeException e) {
            LOGGER.error("Error creating project: {}", e.getMessage());
        } catch (InterruptedException e) {
            LOGGER.error("Main thread interrupted: {}", e.getMessage());
            Thread.currentThread().interrupt();
        }
    }
}

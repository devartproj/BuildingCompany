package com.solvd.buildingcompany.examples;

import com.solvd.buildingcompany.utils.MessageLogger;
import com.solvd.buildingcompany.utils.MessageType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Demo class showing MessageLogger usage in multithreaded environment
 */
public class MessageLoggerDemo {
    private static final Logger LOGGER = LogManager.getLogger(MessageLoggerDemo.class);
    private static final int THREAD_COUNT = 5;
    private static final int MESSAGES_PER_THREAD = 3;

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);
        CountDownLatch latch = new CountDownLatch(THREAD_COUNT);

        System.out.println("\n========================================================");
        System.out.println("          MESSAGE LOGGER DEMO STARTING");
        System.out.println("========================================================\n");

        LOGGER.info("Starting MessageLogger demo with {} threads", THREAD_COUNT);

        // Start multiple threads
        for (int i = 0; i < THREAD_COUNT; i++) {
            final int threadNumber = i + 1;
            executor.submit(() -> {
                try {
                    // Получаем экземпляр Singleton
                    MessageLogger logger = MessageLogger.getInstance();

                    Thread.currentThread().setName("BuildingThread-" + threadNumber);
                    String threadName = Thread.currentThread().getName();

                    for (int j = 0; j < MESSAGES_PER_THREAD; j++) {
                                            // Select a random message type
                        MessageType[] types = MessageType.values();
                        MessageType randomType = types[(int) (Math.random() * types.length)];

                        String message = String.format("Message from thread %d (iteration %d)", 
                            threadNumber, j + 1);

                        logger.logMessage(threadName, randomType, message);

                        // Add random delay to simulate work
                        Thread.sleep((long) (Math.random() * 200));
                    }

                    // Add warning/error logging for some threads
                    if (threadNumber % 2 == 0) {
                        logger.logWarning(Thread.currentThread().getName(), 
                            "Warning message from thread " + threadNumber);
                    }

                    if (threadNumber == THREAD_COUNT) {
                        logger.logError(Thread.currentThread().getName(), 
                            "Error demonstration from the last thread");
                    }

                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    LOGGER.error("Thread interrupted: {}", e.getMessage());
                } finally {
                    latch.countDown();
                }
            });
        }

        try {
            // Wait for all threads to complete
            latch.await(10, TimeUnit.SECONDS);

            // Output final statistics
            MessageLogger logger = MessageLogger.getInstance();
            int totalMessages = logger.getMessageCount();

            System.out.println("\n========================================================");
            System.out.println("        MESSAGE LOGGER DEMO COMPLETED");
            System.out.println("        Total messages logged: " + totalMessages);
            System.out.println("========================================================\n");

            LOGGER.info("Demo completed. Total messages logged: {}", totalMessages);

            // Reset counter for future demos
            logger.resetMessageCount();

            // Terminate thread pool
            executor.shutdown();
            if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            LOGGER.error("Main thread interrupted: {}", e.getMessage());
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}

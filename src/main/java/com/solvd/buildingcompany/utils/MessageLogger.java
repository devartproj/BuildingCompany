package com.solvd.buildingcompany.utils;

import com.solvd.buildingcompany.annotations.BuildingOperation;
import com.solvd.buildingcompany.annotations.Priority;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Thread-safe Singleton class for message logging
 * Demonstrates Singleton pattern and multithreaded interaction
 */
@BuildingOperation(
    description = "Thread-safe singleton utility for logging messages across multiple threads",
    priority = Priority.MEDIUM
)
public class MessageLogger {
    private static final Logger LOGGER = LogManager.getLogger(MessageLogger.class);
    private static volatile MessageLogger instance;
    private static final Lock lock = new ReentrantLock();
    private int messageCount;
    private final DateTimeFormatter formatter;

    // Private constructor to prevent instantiation
    private MessageLogger() {
        messageCount = 0;
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        LOGGER.info("MessageLogger singleton instance created");
    }

    /**
     * Thread-safe method to get the singleton instance (double-checked locking)
     * @return MessageLogger instance
     */
    @BuildingOperation(
        description = "Gets the singleton instance of MessageLogger using double-checked locking",
        priority = Priority.HIGH
    )
    public static MessageLogger getInstance() {
        MessageLogger result = instance;
        if (result == null) {
            lock.lock();
            try {
                result = instance;
                if (result == null) {
                    instance = result = new MessageLogger();
                }
            } finally {
                lock.unlock();
            }
        }
        return result;
    }

    /**
     * Method for logging messages with sequential numbering
     * @param threadName thread name
     * @param message message to log
     */
    @BuildingOperation(
        description = "Logs a message with thread information and sequential number",
        priority = Priority.MEDIUM
    )
    public void logMessage(String threadName, String message) {
        lock.lock();
        try {
            messageCount++;
            String timestamp = LocalDateTime.now().format(formatter);
            LOGGER.info("[{}] [{}] Message #{}: {}", timestamp, threadName, messageCount, message);
        } finally {
            lock.unlock();
        }
    }

    /**
     * Logs a message with specified type
     * @param threadName thread name
     * @param type message type
     * @param message message to log
     */
    @BuildingOperation(
        description = "Logs a message with specified type and thread information",
        priority = Priority.MEDIUM
    )
    public void logMessage(String threadName, MessageType type, String message) {
        lock.lock();
        try {
            messageCount++;
            String timestamp = LocalDateTime.now().format(formatter);
            LOGGER.info("[{}] [{}] {} #{}: {}", timestamp, threadName, type, messageCount, message);
        } finally {
            lock.unlock();
        }
    }

    /**
     * Method for logging warnings
     * @param threadName thread name
     * @param message warning message
     */
    public void logWarning(String threadName, String message) {
        logMessage(threadName, MessageType.WARNING, message);
    }

    /**
     * Method for logging errors
     * @param threadName thread name
     * @param message error message
     */
    public void logError(String threadName, String message) {
        logMessage(threadName, MessageType.ERROR, message);
    }

    /**
     * Get total message count
     * @return number of messages
     */
    public int getMessageCount() {
        lock.lock();
        try {
            return messageCount;
        } finally {
            lock.unlock();
        }
    }

    /**
     * Reset message counter
     */
    public void resetMessageCount() {
        lock.lock();
        try {
            messageCount = 0;
            LOGGER.info("Message counter reset to zero");
        } finally {
            lock.unlock();
        }
    }
}

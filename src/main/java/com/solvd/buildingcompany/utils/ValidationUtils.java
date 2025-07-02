package com.solvd.buildingcompany.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Utility class for common validation operations
 */
public class ValidationUtils {
    private static final Logger LOGGER = LogManager.getLogger(ValidationUtils.class);

    private ValidationUtils() {
        // Private constructor to prevent instantiation
    }

    /**
     * Validates that a string is not null or empty
     * 
     * @param value The string to check
     * @param fieldName The name of the field (for error messages)
     * @return true if valid, false otherwise
     */
    public static boolean validateNotEmpty(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            LOGGER.warn("{} cannot be empty", fieldName);
            return false;
        }
        return true;
    }

    /**
     * Validates that a number is positive
     * 
     * @param value The value to check
     * @param fieldName The name of the field (for error messages)
     * @return true if valid, false otherwise
     */
    public static boolean validatePositive(double value, String fieldName) {
        if (value <= 0) {
            LOGGER.warn("{} must be positive, got: {}", fieldName, value);
            return false;
        }
        return true;
    }
}

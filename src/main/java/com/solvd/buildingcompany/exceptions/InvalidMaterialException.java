package com.solvd.buildingcompany.exceptions;

/**
 * Exception thrown when an invalid building material is specified
 */
public class InvalidMaterialException extends Exception {
    public InvalidMaterialException(String message) {
        super(message);
    }

    public InvalidMaterialException(String message, Throwable cause) {
        super(message, cause);
    }
}

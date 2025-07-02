package com.solvd.buildingcompany.exceptions;

/**
 * Exception thrown when a construction site fails safety checks
 */
public class SafetyCheckFailedException extends Exception {
    private final String siteAddress;

    public SafetyCheckFailedException(String message, String siteAddress) {
        super(message);
        this.siteAddress = siteAddress;
    }

    public String getSiteAddress() {
        return siteAddress;
    }
}

package com.solvd.buildingcompany.utils;

/**
 * Enumeration of message types to use with MessageLogger
 */
public enum MessageType {
    INFO("INFO"),
    WARNING("WARNING"),
    ERROR("ERROR"),
    DEBUG("DEBUG"),
    SYSTEM("SYSTEM"),
    SECURITY("SECURITY");

    private final String type;

    MessageType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type;
    }
}

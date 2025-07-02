package com.solvd.buildingcompany.interfaces;

/**
 * Interface for objects that can be inspected for safety
 */
public interface Inspectable {
    boolean performSafetyCheck();

    void issueWarning(String message);

    String getInspectionReport();
}
package com.solvd.buildingcompany.interfaces;

public interface Reportable {
    String generateReport();

    void exportToPDF(String filename);
}
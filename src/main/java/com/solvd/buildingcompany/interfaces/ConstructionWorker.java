package com.solvd.buildingcompany.interfaces;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public interface ConstructionWorker {
    Logger LOGGER = LogManager.getLogger(ConstructionWorker.class);

    void performDuty();

    boolean isCertified();

    default void attendSafetyTraining() {
        LOGGER.info("Attending mandatory safety training");
    }
}
package com.solvd.buildingcompany.models.workers;

import com.solvd.buildingcompany.interfaces.ConstructionWorker;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class AbstractConstructionTeamMember
        extends AbstractWorker
        implements ConstructionWorker {

    private static final Logger LOGGER = LogManager.getLogger(AbstractConstructionTeamMember.class);

    private int experienceLevel;

    public AbstractConstructionTeamMember(
            String name, String position,
            double hourlyRate, int experienceLevel) {
        super(name, position, hourlyRate);
        this.experienceLevel = experienceLevel;
        LOGGER.debug("Team member created: {} ({})", name, position);
    }

    public int getExperienceLevel() {
        return experienceLevel;
    }

    public void setExperienceLevel(int experienceLevel) {
        this.experienceLevel = experienceLevel;
    }

    public abstract boolean canPerformComplexTask();
}

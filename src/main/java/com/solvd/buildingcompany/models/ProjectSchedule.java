package com.solvd.buildingcompany.models;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.util.Map;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

public class ProjectSchedule<T extends Project> {
    private static final Logger LOGGER = LogManager.getLogger(ProjectSchedule.class);

    private NavigableMap<LocalDate, T> scheduledProjects;

    public ProjectSchedule() {
        this.scheduledProjects = new TreeMap<>();
        LOGGER.debug("Created new project schedule");
    }

    public NavigableMap<LocalDate, T> getScheduledProjects() {
        return scheduledProjects;
    }

    public void setScheduledProjects(NavigableMap<LocalDate, T> scheduledProjects) {
        this.scheduledProjects = scheduledProjects;
        LOGGER.debug("Updated project schedule with {} projects", scheduledProjects.size());
    }

    public void scheduleProject(LocalDate startDate, T project) {
        scheduledProjects.put(startDate, project);
        LOGGER.debug("Scheduled project '{}' to start on {}", project.getName(), startDate);
    }

    public T getProject(LocalDate date) {
        return scheduledProjects.get(date);
    }

    public T getNextProject(LocalDate fromDate) {
        Map.Entry<LocalDate, T> entry = scheduledProjects.ceilingEntry(fromDate);
        if (entry != null) {
            LOGGER.debug("Found next project starting on {}", entry.getKey());
            return entry.getValue();
        }
        LOGGER.debug("No projects scheduled after {}", fromDate);
        return null;
    }

    public T getPreviousProject(LocalDate fromDate) {
        Map.Entry<LocalDate, T> entry = scheduledProjects.floorEntry(fromDate);
        if (entry != null) {
            LOGGER.debug("Found previous project starting on {}", entry.getKey());
            return entry.getValue();
        }
        LOGGER.debug("No projects scheduled before {}", fromDate);
        return null;
    }

    public NavigableMap<LocalDate, T> getProjectsBetweenDates(LocalDate startDate, LocalDate endDate) {
        return scheduledProjects.subMap(startDate, true, endDate, true);
    }

    public boolean removeScheduledProject(LocalDate date) {
        T removed = scheduledProjects.remove(date);
        if (removed != null) {
            LOGGER.debug("Removed project '{}' from schedule", removed.getName());
            return true;
        }
        return false;
    }

    public int getScheduledProjectCount() {
        return scheduledProjects.size();
    }

    public void clearSchedule() {
        scheduledProjects.clear();
        LOGGER.debug("Cleared project schedule");
    }

    public boolean isDateAvailable(LocalDate date) {
        return !scheduledProjects.containsKey(date);
    }

    public LocalDate getEarliestAvailableDate(LocalDate afterDate) {
        LocalDate checkDate = afterDate;
        while (!isDateAvailable(checkDate)) {
            checkDate = checkDate.plusDays(1);
        }
        LOGGER.debug("Found earliest available date: {}", checkDate);
        return checkDate;
    }

    public void rescheduleProject(LocalDate oldDate, LocalDate newDate) {
        T project = scheduledProjects.remove(oldDate);
        if (project != null) {
            scheduledProjects.put(newDate, project);
            LOGGER.debug("Rescheduled project '{}' from {} to {}", project.getName(), oldDate, newDate);
        } else {
            LOGGER.debug("No project found on {} to reschedule", oldDate);
        }
    }
}

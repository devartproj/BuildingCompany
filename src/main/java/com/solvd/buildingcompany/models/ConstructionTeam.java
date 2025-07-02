package com.solvd.buildingcompany.models;

import com.solvd.buildingcompany.models.workers.AbstractConstructionTeamMember;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class ConstructionTeam {
    private static final Logger LOGGER = LogManager.getLogger(ConstructionTeam.class);

    private List<AbstractConstructionTeamMember> members;

    public ConstructionTeam() {
        this.members = new ArrayList<>();
        LOGGER.debug("Construction team created");
    }

    public void addMember(AbstractConstructionTeamMember member) {
        members.add(member);
        LOGGER.debug("Team member added: {} ({})", member.getName(), member.getPosition());
    }

    public List<AbstractConstructionTeamMember> getMembers() {
        return members;
    }

    public int getSize() {
        return members.size();
    }

    public boolean hasQualifiedMember(String position) {
        boolean result = members.stream()
                .anyMatch(m -> m.getPosition().equals(position) && m.isCertified());
        LOGGER.debug("Qualified '{}' check: {}", position, result);
        return result;
    }

    public double calculateTeamCost(int hours) {
        double cost = members.stream()
                .mapToDouble(m -> m.calculateMonthlySalary(hours))
                .sum();
        LOGGER.debug("Team cost calculated: ${}", cost);
        return cost;
    }

    @Override
    public String toString() {
        return String.format("ConstructionTeam: %d members", members.size());
    }
}

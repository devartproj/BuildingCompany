package com.solvd.buildingcompany.models;

import com.solvd.buildingcompany.models.workers.AbstractConstructionTeamMember;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

public class ConstructionTeam<T extends AbstractConstructionTeamMember> {
    private static final Logger LOGGER = LogManager.getLogger(ConstructionTeam.class);

    private List<T> members;
    private Map<String, List<T>> membersByPosition;
    private Set<String> uniqueSpecializations;

    public ConstructionTeam() {
        this.members = new ArrayList<>();
        this.membersByPosition = new HashMap<>();
        this.uniqueSpecializations = new LinkedHashSet<>();
        LOGGER.debug("Construction team created");
    }

    public void addMember(T member) {
        members.add(member);

        // Update position map
        String position = member.getPosition();
        if (!membersByPosition.containsKey(position)) {
            membersByPosition.put(position, new ArrayList<>());
        }
        membersByPosition.get(position).add(member);

        // Track unique specialization
        uniqueSpecializations.add(member.getPosition());

        LOGGER.debug("Team member added: {} ({})", member.getName(), member.getPosition());
    }

    public List<T> getMembers() {
        return members;
    }

    public List<T> getMembersByPosition(String position) {
        return membersByPosition.getOrDefault(position, Collections.emptyList());
    }

    public Set<String> getUniqueSpecializations() {
        return uniqueSpecializations;
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

    public T getTeamLead() {
        if (members.isEmpty()) {
            return null;
        }

        // Get the member with the highest experience level
        return members.stream()
                .max(Comparator.comparingInt(AbstractConstructionTeamMember::getExperienceLevel))
                .orElse(null);
    }

    @Override
    public String toString() {
        return String.format("ConstructionTeam: %d members, %d specializations", 
            members.size(), uniqueSpecializations.size());
    }
}

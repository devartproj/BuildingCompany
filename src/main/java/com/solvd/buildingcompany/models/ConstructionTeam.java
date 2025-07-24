package com.solvd.buildingcompany.models;

import com.solvd.buildingcompany.models.workers.AbstractConstructionTeamMember;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.stream.Collectors;

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
        membersByPosition.computeIfAbsent(position, k -> new ArrayList<>()).add(member);

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

    /**
     * Finds team members with experience level greater than specified value
     * @param minExperience The minimum experience level
     * @return List of members with experience above the threshold
     */
    public List<T> getExperiencedMembers(int minExperience) {
        return members.stream()
                .filter(member -> member.getExperienceLevel() > minExperience)
                .collect(Collectors.toList());
    }

    /**
     * Gets a map of positions to number of members with that position
     * @return Map with positions and counts
     */
    public Map<String, Long> getMemberCountByPosition() {
        return members.stream()
                .collect(Collectors.groupingBy(
                    AbstractConstructionTeamMember::getPosition,
                    Collectors.counting()
                ));
    }

    @Override
    public String toString() {
        return String.format("ConstructionTeam: %d members, %d specializations", 
            members.size(), uniqueSpecializations.size());
    }
}

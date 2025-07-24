package com.solvd.buildingcompany.interfaces;

import com.solvd.buildingcompany.models.workers.AbstractConstructionTeamMember;

/**
 * Functional interface for filtering team members
 */
@FunctionalInterface
public interface TeamFilter<T extends AbstractConstructionTeamMember> {
    /**
     * Tests if the team member matches the filter criteria
     * @param member The team member to test
     * @return true if the member matches, false otherwise
     */
    boolean test(T member);
}

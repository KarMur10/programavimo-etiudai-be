package com.programavimoetiudaibe.algorithms.winningIsTheOnlyThing;

import com.programavimoetiudaibe.entities.TournamentStandings;
import org.springframework.stereotype.Component;

import java.util.*;


@Component
public class General {

    public void UpdateStandings (UUID winnerId, UUID loserId, List<TournamentStandings> standings) {
        standings.stream()
                .filter(x -> x.getId().equals(winnerId))
                .findFirst()
                .ifPresent(x -> x.setCurrentRecordWins(x.getCurrentRecordWins() + 1));

        standings.stream()
                .filter(x -> x.getId().equals(loserId))
                .findFirst()
                .ifPresent(x -> x.setCurrentRecordLosses(x.getCurrentRecordLosses() + 1));
    }

    public void SortStandings (List<TournamentStandings> standings) {
        standings.sort(Comparator
                .comparing(TournamentStandings::getCurrentRecordWins)
                .thenComparing(TournamentStandings::getPower)
                .reversed());
    }
}

package com.programavimoetiudaibe.algorithms;

import com.programavimoetiudaibe.entities.TournamentParticipant;
import com.programavimoetiudaibe.entities.TournamentStandings;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

@Component
public class WinningIsTheOnlyThing {
    public List<TournamentStandings> RoundRobinOrder (List<TournamentParticipant> participants, List<TournamentStandings> standings) {
        IntStream.range(0, participants.size())
                .forEach(i -> IntStream.range(i + 1, participants.size())
                        .forEach(j -> ResolveMatchup(participants.get(i), participants.get(j), standings)));

        List<TournamentStandings> finalStandings = standings
                .stream()
                .sorted(Comparator.comparing(TournamentStandings::getCurrentRecordWins).thenComparing(TournamentStandings::getId).reversed())
                .toList();

        IntStream.range(0, finalStandings.size())
                .forEach(i -> finalStandings.get(i).setStanding(i + 1));

        return finalStandings;
    }

    private void ResolveMatchup (TournamentParticipant participant1, TournamentParticipant participant2, List<TournamentStandings> standings) {
        int winnerId;
        int loserId;
        int n = FindN(standings.size());

        if(DoesParticipant1Win(participant1, participant2, n)) {
            winnerId = participant1.getId();
            loserId = participant2.getId();
        } else {
            winnerId = participant2.getId();
            loserId = participant1.getId();
        }
        standings.stream()
                .filter(x -> x.getId() == winnerId)
                .findFirst()
                .ifPresent(x -> x.setCurrentRecordWins(x.getCurrentRecordWins() + 1));

        standings.stream()
                .filter(x -> x.getId() == loserId)
                .findFirst()
                .ifPresent(x -> x.setCurrentRecordLosses(x.getCurrentRecordLosses() + 1));
    }

    private boolean DoesParticipant1Win (TournamentParticipant participant1, TournamentParticipant participant2, int n) {
        double participant1WinChance = 0.5 + (participant1.getPower() - participant2.getPower()) / Math.pow(2, (n + 1));

        Random random = new Random();

        return random.nextDouble() <= participant1WinChance;
    }

    private int FindN (int participantNum) {
        return Integer.toBinaryString(participantNum).length() - 1;
    }
}

package com.programavimoetiudaibe.algorithms.winningIsTheOnlyThing;

import com.programavimoetiudaibe.entities.TournamentMatchups;
import com.programavimoetiudaibe.entities.TournamentParticipant;
import com.programavimoetiudaibe.entities.TournamentStandings;
import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.IntStream;

@Component
public class RoundRobin {
    private final General generalAlgorithms;

    @Autowired
    public RoundRobin(General generalAlgorithms) {
        this.generalAlgorithms = generalAlgorithms;
    }

    public List<TournamentStandings> RoundRobinOrder (
            List<TournamentParticipant> participants,
            List<TournamentStandings> standings,
            List<TournamentMatchups> individualResults,
            int n
    ) {
        IntStream.range(0, participants.size())
                .forEach(i -> IntStream.range(i + 1, participants.size())
                        .forEach(j -> ResolveMatchup(
                                participants.get(i),
                                participants.get(j),
                                standings,
                                individualResults,
                                n
                        )));

        generalAlgorithms.SortStandings(standings);

        IntStream.range(0, standings.size())
                .forEach(i -> standings.get(i).setStanding(i + 1));

        return standings;
    }

    private void ResolveMatchup (
            TournamentParticipant participant1,
            TournamentParticipant participant2,
            List<TournamentStandings> standings,
            List<TournamentMatchups> individualResults,
            int n
    ) {
        UUID winnerId;
        UUID loserId;

        if(DoesParticipant1Win(participant1, participant2, n)) {
            winnerId = participant1.getId();
            loserId = participant2.getId();
        } else {
            winnerId = participant2.getId();
            loserId = participant1.getId();
        }

        generalAlgorithms.UpdateStandings(winnerId, loserId, standings);

        individualResults.add(TournamentMatchups.builder()
                .id(UUID.randomUUID())
                .contestantsIds(Pair.with(participant1.getId(), participant2.getId()))
                .winnerId(winnerId)
                .build()
        );
    }

    private boolean DoesParticipant1Win (TournamentParticipant participant1, TournamentParticipant participant2, int n) {
        Random random = new Random();

        return random.nextDouble() <= 0.5 + (participant1.getPower() - participant2.getPower()) / Math.pow(2, (n + 1));
    }
}

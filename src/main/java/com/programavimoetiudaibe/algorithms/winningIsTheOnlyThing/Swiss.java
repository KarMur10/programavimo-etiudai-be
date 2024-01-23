package com.programavimoetiudaibe.algorithms.winningIsTheOnlyThing;

import com.programavimoetiudaibe.entities.TournamentMatchups;
import com.programavimoetiudaibe.entities.TournamentParticipant;
import com.programavimoetiudaibe.entities.TournamentStandings;
import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class Swiss {
    private final General generalAlgorithms;

    @Autowired
    public Swiss(General generalAlgorithms) {
        this.generalAlgorithms = generalAlgorithms;
    }

    public List<TournamentStandings> SwissOrder (
            List<TournamentParticipant> participants,
            List<TournamentStandings> standings,
            List<TournamentMatchups> roundRobinResults
    ) {
        List<TournamentMatchups> matchups = Assemble1stRoundBySnakeSeeding(participants);

        ResolveSingleRound(matchups, participants, roundRobinResults, standings);

        while(!IsOnly1ParticipantUndefeated(standings)) {
            AssembleRoundByPairing(standings, matchups);
            ResolveSingleRound(matchups, participants, roundRobinResults, standings);
        }

        generalAlgorithms.SortStandings(standings);
        ResolveTiesByOppWins(standings, matchups);

        return standings;
    }

    private void ResolveMatchupBasedOnExistingResults(
            TournamentParticipant participant1,
            TournamentParticipant participant2,
            TournamentMatchups existingResult,
            List<TournamentStandings> standingsToUpdate,
            TournamentMatchups individualResultsToUpdate
    ) {
        UUID winnerId;
        UUID loserId;

        if(existingResult.getWinnerId().equals(participant1.getId())) {
            winnerId = participant1.getId();
            loserId = participant2.getId();
        } else {
            winnerId = participant2.getId();
            loserId = participant1.getId();
        }

        generalAlgorithms.UpdateStandings(winnerId, loserId, standingsToUpdate);

        individualResultsToUpdate.setWinnerId(winnerId);
    }

    private void ResolveSingleRound(
            List<TournamentMatchups> roundMatchups,
            List<TournamentParticipant> participants,
            List<TournamentMatchups> roundRobinResults,
            List<TournamentStandings> standings
    ) {
        IntStream.range(roundMatchups.size() - participants.size() / 2, roundMatchups.size())
                .forEach(i -> {
                    UUID participant1 = roundMatchups.get(i).getContestantsIds().getValue0();
                    UUID participant2 = roundMatchups.get(i).getContestantsIds().getValue1();
                    ResolveMatchupBasedOnExistingResults(
                            participants.stream()
                                    .filter(x ->
                                            x.getId().equals(participant1))
                                    .findFirst()
                                    .orElseThrow(),
                            participants.stream()
                                    .filter(x ->
                                            x.getId().equals(participant2))
                                    .findFirst()
                                    .orElseThrow(),
                            roundRobinResults.stream()
                                    .filter(x ->
                                            x.getContestantsIds().equals(Pair.with(participant1, participant2)) ||
                                            x.getContestantsIds().equals(Pair.with(participant2, participant1)))
                                    .findFirst()
                                    .orElseThrow(),
                            standings,
                            roundMatchups.get(i)
                    );
                });
    }

    private boolean IsOnly1ParticipantUndefeated (List<TournamentStandings> standings) {
        return standings.stream().filter(x -> x.getCurrentRecordLosses() == 0).count() == 1;
    }

    private List<TournamentMatchups> Assemble1stRoundBySnakeSeeding (List<TournamentParticipant> participants) {
        List<TournamentMatchups> firstRoundMatchups = new ArrayList<>();

        IntStream.range(0, participants.size() / 2)
                .forEach(i -> firstRoundMatchups.add(TournamentMatchups.builder()
                        .id(UUID.randomUUID())
                        .contestantsIds(Pair.with(participants.get(i).getId(), participants.get(participants.size() - 1 - i).getId()))
                        .build()
                ));

        return firstRoundMatchups;
    }

    public void AssembleRoundByPairing (List<TournamentStandings> standings, List<TournamentMatchups> roundMatchups) {
        generalAlgorithms.SortStandings(standings);
        ResolveTiesByOppWins(standings, roundMatchups);
        List<TournamentStandings> standingsForPairing = new ArrayList<>(standings);

        int i = 1;
        while (!standingsForPairing.isEmpty()) {
            int finalI = i;
            if(roundMatchups.stream().noneMatch(x ->
                    x.getContestantsIds().equals(Pair.with(standingsForPairing.get(0).getId(), standingsForPairing.get(finalI).getId())) ||
                            x.getContestantsIds().equals(Pair.with(standingsForPairing.get(finalI).getId(), standingsForPairing.get(0).getId())))
            ) {
                roundMatchups.add(TournamentMatchups.builder()
                        .id(UUID.randomUUID())
                        .contestantsIds(Pair.with(standingsForPairing.get(0).getId(), standingsForPairing.get(i).getId()))
                        .build());

                standingsForPairing.remove(finalI);
                standingsForPairing.remove(0);

                i = 1;
            } else {
                i++;
            }
        }
    }

    public void ResolveTiesByOppWins(List<TournamentStandings> standings, List<TournamentMatchups> currentResults) {
        int i = 0;
        int tiedIndex = -1;
        List<TournamentStandings> tiesToSort = new ArrayList<>();

        while(i < standings.size()) {
            if(tiesToSort.isEmpty()) {
                if(tiedIndex == -1) {
                    tiedIndex = i;
                }
                tiesToSort.add(standings.get(i));

                standings.remove(i);
            } else if(tiesToSort.get(0).getCurrentRecordWins() == standings.get(i).getCurrentRecordWins()) {
                tiesToSort.add(standings.get(i));

                standings.remove(i);
            } else {
                List<TournamentStandings> sortedStandings = CalculateOppWinsAndSort(tiesToSort, currentResults, standings);

                if(tiedIndex >= standings.size()) {
                    standings.addAll(sortedStandings);
                } else {
                    standings.addAll(tiedIndex, sortedStandings);
                }

                tiedIndex = -1;
                i = i + tiesToSort.size();
                tiesToSort.clear();
            }
        }

        if(!tiesToSort.isEmpty()) {
            List<TournamentStandings> sortedStandings = CalculateOppWinsAndSort(tiesToSort, currentResults, standings);

            standings.addAll(tiedIndex, sortedStandings);
        }
    }

    private List<TournamentStandings> CalculateOppWinsAndSort(
            List<TournamentStandings> sublistOfTiedParticipantStandings,
            List<TournamentMatchups> currentResults,
            List<TournamentStandings> standings
    ) {
        List<Integer> sums = new ArrayList<>();

        IntStream.range(0, sublistOfTiedParticipantStandings.size())
                .forEach(x ->
                    sums.add(currentResults.stream()
                            .filter(matches -> matches.getWinnerId().equals(sublistOfTiedParticipantStandings.get(x).getId()))
                            .map(matchesWon ->
                                    matchesWon.getContestantsIds().getValue0().equals(matchesWon.getWinnerId()) ?
                                            matchesWon.getContestantsIds().getValue1() :
                                            matchesWon.getContestantsIds().getValue0())
                            .map(loserId -> standings.stream()
                                    .filter(opp -> opp.getId().equals(loserId))
                                    .findFirst()
                                    .orElse(
                                            sublistOfTiedParticipantStandings.stream()
                                                    .filter(opp -> opp.getId().equals(loserId))
                                                    .findFirst()
                                                    .orElseThrow(() -> new RuntimeException("didnt find a match")))
                                    .getCurrentRecordWins())
                            .mapToInt(Integer::intValue).sum()
                    ));

        List<Pair<TournamentStandings, Integer>> tiedParticipantsAndOppWins = new ArrayList<>();

        IntStream.range(0, sublistOfTiedParticipantStandings.size())
                .forEach(i -> tiedParticipantsAndOppWins.add(Pair.with(sublistOfTiedParticipantStandings.get(i), sums.get(i))));

        tiedParticipantsAndOppWins.sort((pair1, pair2) -> Integer.compare(pair2.getValue1(), pair1.getValue1()));

        return tiedParticipantsAndOppWins.stream()
                .map(Pair::getValue0)
                .collect(Collectors.toCollection(ArrayList::new));
    }
}

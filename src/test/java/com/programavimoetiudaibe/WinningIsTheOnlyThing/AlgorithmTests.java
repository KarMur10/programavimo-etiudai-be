package com.programavimoetiudaibe.WinningIsTheOnlyThing;

import com.programavimoetiudaibe.algorithms.winningIsTheOnlyThing.General;
import com.programavimoetiudaibe.algorithms.winningIsTheOnlyThing.Swiss;
import com.programavimoetiudaibe.entities.TournamentMatchups;
import com.programavimoetiudaibe.entities.TournamentStandings;
import org.javatuples.Pair;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SpringBootTest
public class AlgorithmTests {

    General generalAlgorithms = new General();
    Swiss swiss = new Swiss(generalAlgorithms);

    List<TournamentStandings> standings = new ArrayList<>(List.of(
            TournamentStandings.builder()
                    .id(UUID.fromString("cedd6dbb-f061-45c3-932a-89efe4cbee56"))
                    .name("Contestant 1")
                    .power(2)
                    .currentRecordWins(2)
                    .currentRecordLosses(0)
                    .standing(0)
                    .build(),
            TournamentStandings.builder()
                    .id(UUID.fromString("07a29ee7-35ce-4419-86cb-edded75645e6"))
                    .name("Contestant 2")
                    .power(2)
                    .currentRecordWins(2)
                    .currentRecordLosses(0)
                    .standing(0)
                    .build(),
            TournamentStandings.builder()
                    .id(UUID.fromString("5f429ee7-35ce-4419-86cb-edded75645e6"))
                    .name("mid contestant")
                    .power(2)
                    .currentRecordWins(1)
                    .currentRecordLosses(1)
                    .standing(0)
                    .build(),
            TournamentStandings.builder()
                    .id(UUID.fromString("ac579ee7-35ce-4419-86cb-edded75645e6"))
                    .name("weak contestant")
                    .power(1)
                    .currentRecordWins(0)
                    .currentRecordLosses(2)
                    .standing(0)
                    .build(),
            TournamentStandings.builder()
                    .id(UUID.fromString("cd74dee7-35ce-4419-86cb-edded75645e6"))
                    .name("another weak contestant")
                    .power(1)
                    .currentRecordWins(0)
                    .currentRecordLosses(2)
                    .standing(0)
                    .build()
    ));
    List<TournamentMatchups> results = new ArrayList<>(List.of(
            TournamentMatchups.builder()
                    .id(UUID.fromString("250e63fa-5fb1-4a43-9be5-836921eb2572"))
                    .contestantsIds(Pair.with(UUID.fromString("cedd6dbb-f061-45c3-932a-89efe4cbee56"), UUID.fromString("d2b332c1-6a13-4170-8eaa-b7b8a63b2bdf")))
                    .winnerId(UUID.fromString("cedd6dbb-f061-45c3-932a-89efe4cbee56"))
                    .build(),
            TournamentMatchups.builder()
                    .id(UUID.fromString("ea0e63fa-5fb1-4a43-9be5-836921eb2572"))
                    .contestantsIds(Pair.with(UUID.fromString("07a29ee7-35ce-4419-86cb-edded75645e6"), UUID.fromString("d2b332c1-6a13-4170-8eaa-b7b8a63b2bdf")))
                    .winnerId(UUID.fromString("07a29ee7-35ce-4419-86cb-edded75645e6"))
                    .build(),
            TournamentMatchups.builder()
                    .id(UUID.fromString("8f0e63fa-5fb1-4a43-9be5-836921eb2572"))
                    .contestantsIds(Pair.with(UUID.fromString("cedd6dbb-f061-45c3-932a-89efe4cbee56"), UUID.fromString("cd74dee7-35ce-4419-86cb-edded75645e6")))
                    .winnerId(UUID.fromString("cedd6dbb-f061-45c3-932a-89efe4cbee56"))
                    .build(),
            TournamentMatchups.builder()
                    .id(UUID.fromString("780e63fa-5fb1-4a43-9be5-836921eb2572"))
                    .contestantsIds(Pair.with(UUID.fromString("5f429ee7-35ce-4419-86cb-edded75645e6"), UUID.fromString("07a29ee7-35ce-4419-86cb-edded75645e6")))
                    .winnerId(UUID.fromString("07a29ee7-35ce-4419-86cb-edded75645e6"))
                    .build(),
            TournamentMatchups.builder()
                    .id(UUID.fromString("9a9e63fa-5fb1-4a43-9be5-836921eb2572"))
                    .contestantsIds(Pair.with(UUID.fromString("07a29ee7-35ce-4419-86cb-edded75645e6"), UUID.fromString("cedd6dbb-f061-45c3-932a-89efe4cbee56")))
                    .winnerId(UUID.fromString("07a29ee7-35ce-4419-86cb-edded75645e6"))
                    .build()
    ));

    @Test
    public void testLambda() {
//        List<Integer> sums = new ArrayList<>();
//
//        for (int x = 0; x < sublistOfTiedParticipantStandings.size(); x++) {
//            UUID winnerId = sublistOfTiedParticipantStandings.get(x).getId();
//            for (TournamentIndividualResults matchesWon : results.stream()
//                    .filter(matches -> matches.getWinnerId().equals(winnerId))
//                    .toList()) {
//                UUID loserId = matchesWon.getContestantsIds().getValue0().equals(matchesWon.getWinnerId()) ?
//                        matchesWon.getContestantsIds().getValue1() :
//                        matchesWon.getContestantsIds().getValue0();
//                for (TournamentStandings opp : standings) {
//                    if (opp.getId().equals(loserId)) {
//                        sums.add(opp.getCurrentRecordWins());
//                        break;
//                    }
//                }
//
//                for (TournamentStandings opp : sublistOfTiedParticipantStandings) {
//                    if (opp.getId().equals(loserId)) {
//                        sums.add(opp.getCurrentRecordWins());
//                        break;
//                    }
//                }
//            }
//        }
        Assert.isTrue(results.get(0).getContestantsIds().getValue1().equals(standings.get(3).getId()), "fail!");
    }

    @Test
    public void testResolveTiesByOppWins() {
        swiss.ResolveTiesByOppWins(standings, results);

        Assert.isTrue(standings.equals(new ArrayList<>(List.of(
                TournamentStandings.builder()
                        .id(UUID.fromString("07a29ee7-35ce-4419-86cb-edded75645e6"))
                        .name("Contestant 2")
                        .power(2)
                        .currentRecordWins(2)
                        .currentRecordLosses(0)
                        .standing(0)
                        .build(),
                TournamentStandings.builder()
                        .id(UUID.fromString("cedd6dbb-f061-45c3-932a-89efe4cbee56"))
                        .name("Contestant 1")
                        .power(2)
                        .currentRecordWins(2)
                        .currentRecordLosses(0)
                        .standing(0)
                        .build(),
                TournamentStandings.builder()
                        .id(UUID.fromString("5f429ee7-35ce-4419-86cb-edded75645e6"))
                        .name("mid contestant")
                        .power(2)
                        .currentRecordWins(1)
                        .currentRecordLosses(1)
                        .standing(0)
                        .build(),
                TournamentStandings.builder()
                        .id(UUID.fromString("d2b332c1-6a13-4170-8eaa-b7b8a63b2bdf"))
                        .name("weak contestant")
                        .power(1)
                        .currentRecordWins(0)
                        .currentRecordLosses(2)
                        .standing(0)
                        .build(),
                TournamentStandings.builder()
                        .id(UUID.fromString("cd74dee7-35ce-4419-86cb-edded75645e6"))
                        .name("another weak contestant")
                        .power(1)
                        .currentRecordWins(0)
                        .currentRecordLosses(2)
                        .standing(0)
                        .build()))), "sort failed");
    }

    @Test
    public void TestAssembleRoundByPairing() {

        swiss.AssembleRoundByPairing(standings, results);

        Assert.isTrue(results.stream().anyMatch(matchup ->
                matchup.getContestantsIds().equals(
                        Pair.with(UUID.fromString("07a29ee7-35ce-4419-86cb-edded75645e6"),
                                UUID.fromString("cd74dee7-35ce-4419-86cb-edded75645e6")))
        ), "pairing failed");
    }
}

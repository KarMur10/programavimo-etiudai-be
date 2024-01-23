package com.programavimoetiudaibe.services;

import com.programavimoetiudaibe.algorithms.winningIsTheOnlyThing.RoundRobin;
import com.programavimoetiudaibe.algorithms.winningIsTheOnlyThing.Swiss;
import com.programavimoetiudaibe.entities.TournamentMatchups;
import com.programavimoetiudaibe.entities.TournamentParticipant;
import com.programavimoetiudaibe.entities.TournamentStandings;
import com.programavimoetiudaibe.mappers.WinningIsTheOnlyThingMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WinningIsTheOnlyThingService {
    private final RoundRobin roundRobinAlgorithms;
    private final Swiss swissAlgorithms;

    public int GetParticipantNum(int exponentOf2ForParticipantNum) {
        return (int) Math.pow(2, exponentOf2ForParticipantNum);
    }

    public List<TournamentParticipant> GetGeneratedParticipants (int exponentOf2ForParticipantNum) {
        return GenerateParticipants((int) Math.pow(2, exponentOf2ForParticipantNum));
    }

    public List<TournamentStandings> GetRoundRobinOrder(int exponentOf2ForParticipantNum) {
        List<TournamentParticipant> participants = GenerateParticipants((int) Math.pow(2, exponentOf2ForParticipantNum));
        List<TournamentStandings> standings = InitializeStandings(participants);
        List<TournamentMatchups> individualResults = new ArrayList<>();

        return roundRobinAlgorithms.RoundRobinOrder(participants, standings, individualResults, exponentOf2ForParticipantNum);
    }

    public List<TournamentMatchups> GetRoundRobinIndividualResults(int exponentOf2ForParticipantNum) {
        List<TournamentParticipant> participants = GenerateParticipants((int) Math.pow(2, exponentOf2ForParticipantNum));
        List<TournamentStandings> standings = InitializeStandings(participants);
        List<TournamentMatchups> individualResults = new ArrayList<>();

        roundRobinAlgorithms.RoundRobinOrder(participants, standings, individualResults, exponentOf2ForParticipantNum);

        return individualResults;
    }

    public List<TournamentStandings> GetSwissOrderBasedOnRoundRobinResults(int exponentOf2ForParticipantNum) {
        List<TournamentParticipant> participants = GenerateParticipants((int) Math.pow(2, exponentOf2ForParticipantNum));
        List<TournamentStandings> roundRobinStandings = InitializeStandings(participants);
        List<TournamentStandings> swissStandings = InitializeStandings(participants);
        List<TournamentMatchups> individualRoundRobinResults = new ArrayList<>();


        roundRobinAlgorithms.RoundRobinOrder(participants, roundRobinStandings, individualRoundRobinResults, exponentOf2ForParticipantNum);

        return swissAlgorithms.SwissOrder(participants, swissStandings, individualRoundRobinResults);
    }

    private List<TournamentParticipant> GenerateParticipants(int participantNum) {
        List<TournamentParticipant> participants = new ArrayList<>();

        for (int i = 1; i <= participantNum; i++) {
            participants.add(TournamentParticipant.builder()
                    .id(UUID.randomUUID())
                    .name(String.format("participant %s", i))
                    .power(i)
                    .build());
        }

        return participants;
    }

    private List<TournamentStandings> InitializeStandings(List<TournamentParticipant> participants) {
        return participants.stream()
                .map(WinningIsTheOnlyThingMapper::TournamentParticipantToStandings)
                .collect(Collectors.toCollection(ArrayList::new));
    }
}

package com.programavimoetiudaibe.services;

import com.programavimoetiudaibe.algorithms.WinningIsTheOnlyThingAlgorithms;
import com.programavimoetiudaibe.entities.TournamentIndividualResults;
import com.programavimoetiudaibe.entities.TournamentParticipant;
import com.programavimoetiudaibe.entities.TournamentStandings;
import com.programavimoetiudaibe.mappers.WinningIsTheOnlyThingMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class WinningIsTheOnlyThingService {
    private final WinningIsTheOnlyThingAlgorithms witotAlgorithms;

    public List<TournamentParticipant> GenerateParticipants(int participantNum) {
        return GenerateParticipants(participantNum, 1);
    }

    public List<TournamentParticipant> GenerateParticipants(int participantNum, int powerIncrement) {
        List<TournamentParticipant> participants = new ArrayList<>();

        for (int i = 1; i <= participantNum; i++) {
            participants.add(TournamentParticipant.builder()
                    .id(UUID.randomUUID())
                    .name(String.format("participant %s", i))
                    .power(powerIncrement * i)
                    .build());
        }

        return participants;
    }

    public List<TournamentStandings> GetRoundRobinOrder(int participantNum) {
        List<TournamentParticipant> participants = GenerateParticipants(participantNum);
        List<TournamentStandings> standings = InitializeStandings(participants);
        List<TournamentIndividualResults> individualResults = new ArrayList<>();

        return witotAlgorithms.RoundRobinOrder(participants, standings, individualResults);
    }

    public List<TournamentIndividualResults> GetRoundRobinIndividualResults(int participantNum) {
        List<TournamentParticipant> participants = GenerateParticipants(participantNum);
        List<TournamentStandings> standings = InitializeStandings(participants);
        List<TournamentIndividualResults> individualResults = new ArrayList<>();

        witotAlgorithms.RoundRobinOrder(participants, standings, individualResults);

        return individualResults;
    }

    public List<TournamentStandings> GetSwissOrder(int participantNum) {
        List<TournamentParticipant> participants = GenerateParticipants(participantNum);
        List<TournamentStandings> roundRobinStandings = InitializeStandings(participants);
        List<TournamentStandings> swissStandings = InitializeStandings(participants);
        List<TournamentIndividualResults> individualRoundRobinResults = new ArrayList<>();


        witotAlgorithms.RoundRobinOrder(participants, roundRobinStandings, individualRoundRobinResults);

        return witotAlgorithms.SwissOrder(participants, swissStandings, individualRoundRobinResults);
    }

    private List<TournamentStandings> InitializeStandings(List<TournamentParticipant> participants) {
        return participants.stream()
                .map(WinningIsTheOnlyThingMapper::TournamentParticipantToStandings)
                .toList();
    }
}

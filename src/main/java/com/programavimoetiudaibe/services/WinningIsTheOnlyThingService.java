package com.programavimoetiudaibe.services;

import com.programavimoetiudaibe.algorithms.WinningIsTheOnlyThing;
import com.programavimoetiudaibe.entities.TournamentParticipant;
import com.programavimoetiudaibe.entities.TournamentStandings;
import com.programavimoetiudaibe.mappers.WinningIsTheOnlyThingMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class WinningIsTheOnlyThingService {
    private final WinningIsTheOnlyThing witotAlgorithms;

    public List<TournamentParticipant> GenerateParticipants(int participantNum) {
        return GenerateParticipants(participantNum, 1);
    }

    public List<TournamentParticipant> GenerateParticipants(int participantNum, int powerIncrement) {
        List<TournamentParticipant> participants = new ArrayList<>();

        for (int i = 1; i <= participantNum; i++) {
            participants.add(TournamentParticipant.builder()
                    .id(i)
                    .name(String.format("participant %s", i))
                    .power(powerIncrement * i)
                    .build());
        }

        return participants;
    }

    public List<TournamentStandings> GetRoundRobinOrder(int participantNum) {
        List<TournamentParticipant> participants = GenerateParticipants(participantNum);
        List<TournamentStandings> standings = InitializeStandings(participants);

        return witotAlgorithms.RoundRobinOrder(participants, standings);
    }

    private List<TournamentStandings> InitializeStandings(List<TournamentParticipant> participants) {
        return participants.stream()
                .map(WinningIsTheOnlyThingMapper::TournamentParticipantToStandings)
                .toList();
    }




    public List<TournamentStandings> GetInitializedStandings(int participantNum) {
        List<TournamentParticipant> participants = GenerateParticipants(participantNum);

        return InitializeStandings(participants);
    }

    public int testN(int participantNum) {
        return Integer.toBinaryString(participantNum).length() - 1;
    }
}

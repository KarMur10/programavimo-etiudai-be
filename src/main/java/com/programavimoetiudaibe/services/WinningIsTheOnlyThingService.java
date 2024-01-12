package com.programavimoetiudaibe.services;

import com.programavimoetiudaibe.entities.TournamentParticipant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class WinningIsTheOnlyThingService {
    public List<TournamentParticipant> GenerateParticipants(int participantNum) {
        return GenerateParticipants(participantNum, 1);
    }

    public List<TournamentParticipant> GenerateParticipants(int participantNum, int powerIncrement) {
        List<TournamentParticipant> participants = new ArrayList<>();

        for (int i = 1; i <= participantNum; i++) {
            participants.add(TournamentParticipant.builder()
                    .name(String.format("participant %s", i))
                    .power(powerIncrement * i)
                    .build());
        }

        return participants;
    }

    public List<TournamentParticipant> RoundRobinOrder(int participantNum) {
        // todo
        return GenerateParticipants(participantNum);
    }
}

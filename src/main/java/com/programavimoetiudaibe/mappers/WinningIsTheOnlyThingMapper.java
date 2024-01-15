package com.programavimoetiudaibe.mappers;

import com.programavimoetiudaibe.entities.TournamentParticipant;
import com.programavimoetiudaibe.entities.TournamentStandings;

public class WinningIsTheOnlyThingMapper {
    public static TournamentStandings TournamentParticipantToStandings(TournamentParticipant participant) {
        return TournamentStandings.builder()
                .id(participant.getId())
                .name(participant.getName())
                .power(participant.getPower())
                .build();
    }
}

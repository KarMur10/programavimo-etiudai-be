package com.programavimoetiudaibe.entities;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class TournamentStandings extends TournamentParticipant {
    private int currentStanding;
}

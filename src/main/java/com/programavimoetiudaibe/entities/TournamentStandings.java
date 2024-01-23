package com.programavimoetiudaibe.entities;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Data
@SuperBuilder
public class TournamentStandings {
    private UUID id;
    private String name;
    private int power;
    private int currentRecordWins;
    private int currentRecordLosses;
    private int standing;
}

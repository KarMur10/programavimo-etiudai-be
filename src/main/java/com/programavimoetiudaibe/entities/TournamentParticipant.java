package com.programavimoetiudaibe.entities;

import lombok.experimental.SuperBuilder;
import lombok.Data;

import java.util.UUID;

@Data
@SuperBuilder
public class TournamentParticipant {
    private String name;
    private int power;
}

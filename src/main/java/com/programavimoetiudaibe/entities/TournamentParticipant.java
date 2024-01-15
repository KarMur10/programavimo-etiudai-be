package com.programavimoetiudaibe.entities;

import lombok.experimental.SuperBuilder;
import lombok.Data;


@Data
@SuperBuilder
public class TournamentParticipant {
    private int id;
    private String name;
    private int power;
}

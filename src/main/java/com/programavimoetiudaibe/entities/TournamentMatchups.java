package com.programavimoetiudaibe.entities;

import org.javatuples.Pair;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class TournamentMatchups {
    private UUID id;
    private Pair<UUID, UUID> contestantsIds;
    private UUID winnerId;
}

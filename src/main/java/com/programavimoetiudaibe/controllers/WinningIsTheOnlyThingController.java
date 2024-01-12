package com.programavimoetiudaibe.controllers;

import com.programavimoetiudaibe.entities.TournamentParticipant;
import com.programavimoetiudaibe.services.WinningIsTheOnlyThingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/witot")
public class WinningIsTheOnlyThingController {
    private final WinningIsTheOnlyThingService winningService;

    @GetMapping("/getGeneratedParticipants")
    public List<TournamentParticipant> GetGeneratedParticipants(int participantNum) {
        return winningService.GenerateParticipants(participantNum);
    }

    @GetMapping("/getRoundRobinOrder")
    public List<TournamentParticipant> GetRoundRobinOrder(int participantNum) {
        return winningService.RoundRobinOrder(participantNum);
    }




    @GetMapping
    public String GetResponse() {
        return "pong";
    }
}

package com.programavimoetiudaibe.controllers;

import com.programavimoetiudaibe.entities.TournamentParticipant;
import com.programavimoetiudaibe.entities.TournamentStandings;
import com.programavimoetiudaibe.services.WinningIsTheOnlyThingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/witot")
public class WinningIsTheOnlyThingController {
    private final WinningIsTheOnlyThingService witotService;

    @GetMapping("/getGeneratedParticipants")
    public List<TournamentParticipant> GetGeneratedParticipants(int participantNum) {
        return witotService.GenerateParticipants(participantNum);
    }

    @GetMapping("/getRoundRobinOrder")
    public List<TournamentStandings> GetRoundRobinOrder(int participantNum) {
        return witotService.GetRoundRobinOrder(participantNum);
    }




    @GetMapping("/getInitializedStandings")
    public List<TournamentStandings> GetInintializedStandings(int participantNum) {
        return witotService.GetInitializedStandings(participantNum);
    }

    @GetMapping("/testN")
    public int testN(int participantNum) {
        return witotService.testN(participantNum);
    }
    @GetMapping
    public String GetResponse() {
        return "pong";
    }
}

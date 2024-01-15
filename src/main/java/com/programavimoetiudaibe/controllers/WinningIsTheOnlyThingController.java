package com.programavimoetiudaibe.controllers;

import com.programavimoetiudaibe.entities.TournamentIndividualResults;
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

    @GetMapping("/getSwissOrder")
    public List<TournamentStandings> GetSwissOrder(int participantNum) {
        return witotService.GetSwissOrder(participantNum);
    }

    @GetMapping("/getRoundRobinIndividualResults")
    public List<TournamentIndividualResults> GetRoundRobinIndividualResults(int participantNum) {
        return witotService.GetRoundRobinIndividualResults(participantNum);
    }
}

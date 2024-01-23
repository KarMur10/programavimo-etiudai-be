package com.programavimoetiudaibe.controllers;

import com.programavimoetiudaibe.entities.TournamentMatchups;
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

    @GetMapping("/getParticipantNum")
    public int GetParticipantNum(int exponentOf2ForParticipantNum) {
        return witotService.GetParticipantNum(exponentOf2ForParticipantNum);
    }
    @GetMapping("/getGeneratedParticipants")
    public List<TournamentParticipant> GetGeneratedParticipants(int exponentOf2ForParticipantNum) {
        return witotService.GetGeneratedParticipants(exponentOf2ForParticipantNum);
    }

    @GetMapping("/getRoundRobinOrder")
    public List<TournamentStandings> GetRoundRobinOrder(int exponentOf2ForParticipantNum) {
        return witotService.GetRoundRobinOrder(exponentOf2ForParticipantNum);
    }

    @GetMapping("/getSwissOrderBasedOnRoundRobinResults")
    public List<TournamentStandings> GetSwissOrderBasedOnRoundRobinResults(int exponentOf2ForParticipantNum) {
        return witotService.GetSwissOrderBasedOnRoundRobinResults(exponentOf2ForParticipantNum);
    }

    @GetMapping("/getRoundRobinIndividualResults")
    public List<TournamentMatchups> GetRoundRobinIndividualResults(int exponentOf2ForParticipantNum) {
        return witotService.GetRoundRobinIndividualResults(exponentOf2ForParticipantNum);
    }
}

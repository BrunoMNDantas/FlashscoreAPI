package com.github.brunomndantas.flashscore.api.dataAccess.dtos.match;

import com.github.brunomndantas.flashscore.api.dataAccess.dtos.DTOTests;
import com.github.brunomndantas.flashscore.api.logic.domain.match.Match;
import com.github.brunomndantas.flashscore.api.logic.domain.match.MatchKey;
import com.github.brunomndantas.flashscore.api.logic.domain.match.event.*;
import com.github.brunomndantas.flashscore.api.logic.domain.player.PlayerKey;
import com.github.brunomndantas.flashscore.api.logic.domain.team.TeamKey;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Date;

@SpringBootTest
public class MatchDTOTests extends DTOTests<MatchDTO, Match> {

    @Override
    protected Match getInitialEntity() {
        return new Match(
                new MatchKey("A"),
                new TeamKey("A", "B"),
                new TeamKey("C", "D"),
                3,
                5,
                new Date(),
                new PlayerKey("E", "F"),
                new PlayerKey("G", "H"),
                Arrays.asList(new PlayerKey("I", "J")),
                Arrays.asList(new PlayerKey("K", "L")),
                Arrays.asList(new PlayerKey("M", "N")),
                Arrays.asList(new PlayerKey("O", "P")),
                Arrays.asList(
                        new Card(
                                new Time(Time.Period.FIRST_HALF, 2, 3),
                                new TeamKey("A", "B"),
                                new PlayerKey("C", "D"),
                                Card.Color.RED
                        ),
                        new Goal(
                                new Time(Time.Period.SECOND_HALF, 2, 5),
                                new TeamKey("A", "B"),
                                new PlayerKey("C", "D"),
                                new PlayerKey("E", "F")
                        ),
                        new Penalty(
                                new Time(Time.Period.PENALTIES, 1, 4),
                                new TeamKey("A", "B"),
                                true,
                                new PlayerKey("C", "D")
                        ),
                        new Substitution(
                                new Time(Time.Period.EXTRA_TIME, 2, 5),
                                new TeamKey("A", "B"),
                                new PlayerKey("C", "D"),
                                new PlayerKey("E", "F")
                        )
                )
        );
    }

    @Override
    protected Match getNullSafeInitialEntity() {
        return new Match();
    }

    @Override
    protected MatchDTO getDTO(Match domainEntity) {
        return new MatchDTO(domainEntity);
    }

    @Override
    protected Match getDomainEntity(MatchDTO matchDTO) {
        return matchDTO.toDomainEntity();
    }

}
package com.github.brunomndantas.flashscore.api.serviceInterface.dtos.match.event;

import com.github.brunomndantas.flashscore.api.logic.domain.match.event.Goal;
import com.github.brunomndantas.flashscore.api.logic.domain.match.event.Time;
import com.github.brunomndantas.flashscore.api.logic.domain.player.PlayerKey;
import com.github.brunomndantas.flashscore.api.logic.domain.team.TeamKey;
import com.github.brunomndantas.flashscore.api.serviceInterface.dtos.DTOTests;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class GoalDTOTests extends DTOTests<GoalDTO, Goal> {

    @Override
    protected Goal getInitialEntity() {
        return new Goal(
                new Time(Time.Period.SECOND_HALF, 2, 5),
                new TeamKey("A", "B"),
                new PlayerKey("C", "D"),
                new PlayerKey("E", "F")
        );
    }

    @Override
    protected Goal getNullSafeInitialEntity() {
        return new Goal();
    }

    @Override
    protected GoalDTO getDTO(Goal domainEntity) {
        return new GoalDTO(domainEntity);
    }

    @Override
    protected Goal getDomainEntity(GoalDTO goalDTO) {
        return goalDTO.toDomainEntity();
    }

}
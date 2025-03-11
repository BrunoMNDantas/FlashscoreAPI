package com.github.brunomndantas.flashscore.api.dataAccess.dtos.team;

import com.github.brunomndantas.flashscore.api.dataAccess.dtos.DTOTests;
import com.github.brunomndantas.flashscore.api.logic.domain.player.PlayerKey;
import com.github.brunomndantas.flashscore.api.logic.domain.team.Team;
import com.github.brunomndantas.flashscore.api.logic.domain.team.TeamKey;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

@SpringBootTest
public class TeamDTOTests extends DTOTests<TeamDTO, Team> {

    @Override
    protected Team getInitialEntity() {
        return new Team(
                new TeamKey("A", "B"),
                "C",
                "D",
                1,
                new PlayerKey("A", "B"),
                Arrays.asList(new PlayerKey("C", "D"))
        );
    }

    @Override
    protected Team getNullSafeInitialEntity() {
        return new Team();
    }

    @Override
    protected TeamDTO getDTO(Team domainEntity) {
        return new TeamDTO(domainEntity);
    }

    @Override
    protected Team getDomainEntity(TeamDTO teamDTO) {
        return teamDTO.toDomainEntity();
    }

}
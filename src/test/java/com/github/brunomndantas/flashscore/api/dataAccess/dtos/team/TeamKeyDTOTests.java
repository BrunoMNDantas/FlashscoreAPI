package com.github.brunomndantas.flashscore.api.dataAccess.dtos.team;

import com.github.brunomndantas.flashscore.api.dataAccess.dtos.DTOTests;
import com.github.brunomndantas.flashscore.api.logic.domain.team.TeamKey;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TeamKeyDTOTests extends DTOTests<TeamKeyDTO, TeamKey> {

    @Override
    protected TeamKey getInitialEntity() {
        return new TeamKey("A", "B");
    }

    @Override
    protected TeamKey getNullSafeInitialEntity() {
        return new TeamKey();
    }

    @Override
    protected TeamKeyDTO getDTO(TeamKey domainEntity) {
        return new TeamKeyDTO(domainEntity);
    }

    @Override
    protected TeamKey getDomainEntity(TeamKeyDTO teamKeyDTO) {
        return teamKeyDTO.toDomainEntity();
    }

}
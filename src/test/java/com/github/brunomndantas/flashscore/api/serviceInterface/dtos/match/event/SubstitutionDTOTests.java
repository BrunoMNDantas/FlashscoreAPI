package com.github.brunomndantas.flashscore.api.serviceInterface.dtos.match.event;

import com.github.brunomndantas.flashscore.api.logic.domain.match.event.Substitution;
import com.github.brunomndantas.flashscore.api.logic.domain.match.event.Time;
import com.github.brunomndantas.flashscore.api.logic.domain.player.PlayerKey;
import com.github.brunomndantas.flashscore.api.logic.domain.team.TeamKey;
import com.github.brunomndantas.flashscore.api.serviceInterface.dtos.DTOTests;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SubstitutionDTOTests extends DTOTests<SubstitutionDTO, Substitution> {

    @Override
    protected Substitution getInitialEntity() {
        return new Substitution(
                new Time(Time.Period.EXTRA_TIME, 2, 5),
                new TeamKey("A", "B"),
                new PlayerKey("C", "D"),
                new PlayerKey("E", "F")
        );
    }

    @Override
    protected Substitution getNullSafeInitialEntity() {
        return new Substitution();
    }

    @Override
    protected SubstitutionDTO getDTO(Substitution domainEntity) {
        return new SubstitutionDTO(domainEntity);
    }

    @Override
    protected Substitution getDomainEntity(SubstitutionDTO substitutionDTO) {
        return substitutionDTO.toDomainEntity();
    }

}
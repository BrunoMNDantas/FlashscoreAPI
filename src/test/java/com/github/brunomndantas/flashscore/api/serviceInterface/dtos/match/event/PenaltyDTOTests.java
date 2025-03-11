package com.github.brunomndantas.flashscore.api.serviceInterface.dtos.match.event;

import com.github.brunomndantas.flashscore.api.logic.domain.match.event.Penalty;
import com.github.brunomndantas.flashscore.api.logic.domain.match.event.Time;
import com.github.brunomndantas.flashscore.api.logic.domain.player.PlayerKey;
import com.github.brunomndantas.flashscore.api.logic.domain.team.TeamKey;
import com.github.brunomndantas.flashscore.api.serviceInterface.dtos.DTOTests;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PenaltyDTOTests extends DTOTests<PenaltyDTO, Penalty> {

    @Override
    protected Penalty getInitialEntity() {
        return new Penalty(
                new Time(Time.Period.PENALTIES, 1, 4),
                new TeamKey("A", "B"),
                true,
                new PlayerKey("C", "D")
        );
    }

    @Override
    protected Penalty getNullSafeInitialEntity() {
        return new Penalty();
    }

    @Override
    protected PenaltyDTO getDTO(Penalty domainEntity) {
        return new PenaltyDTO(domainEntity);
    }

    @Override
    protected Penalty getDomainEntity(PenaltyDTO penaltyDTO) {
        return penaltyDTO.toDomainEntity();
    }

}



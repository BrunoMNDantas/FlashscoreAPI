package com.github.brunomndantas.flashscore.api.dataAccess.dtos.match.event;

import com.github.brunomndantas.flashscore.api.dataAccess.dtos.DTOTests;
import com.github.brunomndantas.flashscore.api.logic.domain.match.event.Time;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TimeDTOTests extends DTOTests<TimeDTO, Time> {

    @Override
    protected Time getInitialEntity() {
        return new Time(Time.Period.EXTRA_TIME, 1, 6);
    }

    @Override
    protected Time getNullSafeInitialEntity() {
        return new Time();
    }

    @Override
    protected TimeDTO getDTO(Time domainEntity) {
        return new TimeDTO(domainEntity);
    }

    @Override
    protected Time getDomainEntity(TimeDTO timeDTO) {
        return timeDTO.toDomainEntity();
    }

}
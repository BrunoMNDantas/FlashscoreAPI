package com.github.brunomndantas.flashscore.api.serviceInterface.dtos.sport;

import com.github.brunomndantas.flashscore.api.logic.domain.region.RegionKey;
import com.github.brunomndantas.flashscore.api.logic.domain.sport.Sport;
import com.github.brunomndantas.flashscore.api.logic.domain.sport.SportKey;
import com.github.brunomndantas.flashscore.api.serviceInterface.dtos.DTOTests;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

@SpringBootTest
public class SportDTOTests extends DTOTests<SportDTO, Sport> {

    @Override
    protected Sport getInitialEntity() {
        return new Sport(
                new SportKey("A"),
                "B",
                Arrays.asList(new RegionKey("A", "B"))
        );
    }

    @Override
    protected Sport getNullSafeInitialEntity() {
        return new Sport();
    }

    @Override
    protected SportDTO getDTO(Sport domainEntity) {
        return new SportDTO(domainEntity);
    }

    @Override
    protected Sport getDomainEntity(SportDTO sportDTO) {
        return sportDTO.toDomainEntity();
    }

}
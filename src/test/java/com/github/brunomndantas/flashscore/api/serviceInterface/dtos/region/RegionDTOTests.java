package com.github.brunomndantas.flashscore.api.serviceInterface.dtos.region;

import com.github.brunomndantas.flashscore.api.logic.domain.competition.CompetitionKey;
import com.github.brunomndantas.flashscore.api.logic.domain.region.Region;
import com.github.brunomndantas.flashscore.api.logic.domain.region.RegionKey;
import com.github.brunomndantas.flashscore.api.serviceInterface.dtos.DTOTests;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

@SpringBootTest
public class RegionDTOTests extends DTOTests<RegionDTO, Region> {

    @Override
    protected Region getInitialEntity() {
        return new Region(
                new RegionKey("A", "B"),
                "C",
                Arrays.asList(new CompetitionKey("A", "B", "C"))
        );
    }

    @Override
    protected Region getNullSafeInitialEntity() {
        return new Region();
    }

    @Override
    protected RegionDTO getDTO(Region domainEntity) {
        return new RegionDTO(domainEntity);
    }

    @Override
    protected Region getDomainEntity(RegionDTO regionDTO) {
        return regionDTO.toDomainEntity();
    }

}
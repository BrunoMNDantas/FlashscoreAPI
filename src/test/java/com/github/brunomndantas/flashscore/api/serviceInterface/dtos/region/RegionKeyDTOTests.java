package com.github.brunomndantas.flashscore.api.serviceInterface.dtos.region;

import com.github.brunomndantas.flashscore.api.logic.domain.region.RegionKey;
import com.github.brunomndantas.flashscore.api.serviceInterface.dtos.DTOTests;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RegionKeyDTOTests extends DTOTests<RegionKeyDTO, RegionKey> {

    @Override
    protected RegionKey getInitialEntity() {
        return new RegionKey("A", "B");
    }

    @Override
    protected RegionKey getNullSafeInitialEntity() {
        return new RegionKey();
    }

    @Override
    protected RegionKeyDTO getDTO(RegionKey domainEntity) {
        return new RegionKeyDTO(domainEntity);
    }

    @Override
    protected RegionKey getDomainEntity(RegionKeyDTO regionKeyDTO) {
        return regionKeyDTO.toDomainEntity();
    }

}
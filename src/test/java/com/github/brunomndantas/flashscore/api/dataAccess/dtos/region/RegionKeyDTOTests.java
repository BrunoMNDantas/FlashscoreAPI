package com.github.brunomndantas.flashscore.api.dataAccess.dtos.region;

import com.github.brunomndantas.flashscore.api.dataAccess.dtos.DTOTests;
import com.github.brunomndantas.flashscore.api.logic.domain.region.RegionKey;
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
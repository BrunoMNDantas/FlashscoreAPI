package com.github.brunomndantas.flashscore.api.dataAccess.dtos.sport;

import com.github.brunomndantas.flashscore.api.dataAccess.dtos.DTOTests;
import com.github.brunomndantas.flashscore.api.logic.domain.sport.SportKey;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SportKeyDTOTests extends DTOTests<SportKeyDTO, SportKey> {

    @Override
    protected SportKey getInitialEntity() {
        return new SportKey("A");
    }

    @Override
    protected SportKey getNullSafeInitialEntity() {
        return new SportKey();
    }

    @Override
    protected SportKeyDTO getDTO(SportKey domainEntity) {
        return new SportKeyDTO(domainEntity);
    }

    @Override
    protected SportKey getDomainEntity(SportKeyDTO sportKeyDTO) {
        return sportKeyDTO.toDomainEntity();
    }

}
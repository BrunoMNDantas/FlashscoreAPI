package com.github.brunomndantas.flashscore.api.dataAccess.dtos.season;

import com.github.brunomndantas.flashscore.api.dataAccess.dtos.DTOTests;
import com.github.brunomndantas.flashscore.api.logic.domain.season.SeasonKey;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SeasonKeyDTOTests extends DTOTests<SeasonKeyDTO, SeasonKey> {

    @Override
    protected SeasonKey getInitialEntity() {
        return new SeasonKey("A", "B", "C", "D");
    }

    @Override
    protected SeasonKey getNullSafeInitialEntity() {
        return new SeasonKey();
    }

    @Override
    protected SeasonKeyDTO getDTO(SeasonKey domainEntity) {
        return new SeasonKeyDTO(domainEntity);
    }

    @Override
    protected SeasonKey getDomainEntity(SeasonKeyDTO seasonKeyDTO) {
        return seasonKeyDTO.toDomainEntity();
    }

}
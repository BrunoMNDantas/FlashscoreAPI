package com.github.brunomndantas.flashscore.api.dataAccess.dtos.match;

import com.github.brunomndantas.flashscore.api.dataAccess.dtos.DTOTests;
import com.github.brunomndantas.flashscore.api.logic.domain.match.MatchKey;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MatchKeyDTOTests extends DTOTests<MatchKeyDTO, MatchKey> {

    @Override
    protected MatchKey getInitialEntity() {
        return new MatchKey("A");
    }

    @Override
    protected MatchKey getNullSafeInitialEntity() {
        return new MatchKey();
    }

    @Override
    protected MatchKeyDTO getDTO(MatchKey domainEntity) {
        return new MatchKeyDTO(domainEntity);
    }

    @Override
    protected MatchKey getDomainEntity(MatchKeyDTO matchKeyDTO) {
        return matchKeyDTO.toDomainEntity();
    }

}
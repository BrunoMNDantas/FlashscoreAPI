package com.github.brunomndantas.flashscore.api.dataAccess.dtos.competition;

import com.github.brunomndantas.flashscore.api.dataAccess.dtos.DTOTests;
import com.github.brunomndantas.flashscore.api.logic.domain.competition.CompetitionKey;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CompetitionKeyDTOTests extends DTOTests<CompetitionKeyDTO, CompetitionKey> {

    @Override
    protected CompetitionKey getInitialEntity() {
        return new CompetitionKey("A", "B", "C");
    }

    @Override
    protected CompetitionKey getNullSafeInitialEntity() {
        return new CompetitionKey();
    }

    @Override
    protected CompetitionKeyDTO getDTO(CompetitionKey domainEntity) {
        return new CompetitionKeyDTO(domainEntity);
    }

    @Override
    protected CompetitionKey getDomainEntity(CompetitionKeyDTO competitionKeyDTO) {
        return competitionKeyDTO.toDomainEntity();
    }

}
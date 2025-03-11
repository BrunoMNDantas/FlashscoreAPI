package com.github.brunomndantas.flashscore.api.serviceInterface.dtos.competition;

import com.github.brunomndantas.flashscore.api.logic.domain.competition.Competition;
import com.github.brunomndantas.flashscore.api.logic.domain.competition.CompetitionKey;
import com.github.brunomndantas.flashscore.api.logic.domain.season.SeasonKey;
import com.github.brunomndantas.flashscore.api.serviceInterface.dtos.DTOTests;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

@SpringBootTest
public class CompetitionDTOTests extends DTOTests<CompetitionDTO, Competition> {

    @Override
    protected Competition getInitialEntity() {
        return new Competition(
                new CompetitionKey("A", "B", "C"),
                "D",
                Arrays.asList(new SeasonKey("A", "B", "C", "D"))
        );
    }

    @Override
    protected Competition getNullSafeInitialEntity() {
        return new Competition();
    }

    @Override
    protected CompetitionDTO getDTO(Competition domainEntity) {
        return new CompetitionDTO(domainEntity);
    }

    @Override
    protected Competition getDomainEntity(CompetitionDTO competitionDTO) {
        return competitionDTO.toDomainEntity();
    }

}
package com.github.brunomndantas.flashscore.api.dataAccess.dtos.season;

import com.github.brunomndantas.flashscore.api.dataAccess.dtos.DTOTests;
import com.github.brunomndantas.flashscore.api.logic.domain.match.MatchKey;
import com.github.brunomndantas.flashscore.api.logic.domain.season.Season;
import com.github.brunomndantas.flashscore.api.logic.domain.season.SeasonKey;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

@SpringBootTest
public class SeasonDTOTests extends DTOTests<SeasonDTO, Season> {

    @Override
    protected Season getInitialEntity() {
        return new Season(
                new SeasonKey("A", "B", "C", "D"),
                1,
                2,
                Arrays.asList(new MatchKey("A"))
        );
    }

    @Override
    protected Season getNullSafeInitialEntity() {
        return new Season();
    }

    @Override
    protected SeasonDTO getDTO(Season domainEntity) {
        return new SeasonDTO(domainEntity);
    }

    @Override
    protected Season getDomainEntity(SeasonDTO seasonDTO) {
        return seasonDTO.toDomainEntity();
    }

}
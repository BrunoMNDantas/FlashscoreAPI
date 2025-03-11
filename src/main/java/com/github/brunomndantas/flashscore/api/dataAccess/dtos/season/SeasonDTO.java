package com.github.brunomndantas.flashscore.api.dataAccess.dtos.season;

import com.github.brunomndantas.flashscore.api.dataAccess.dtos.match.MatchKeyDTO;
import com.github.brunomndantas.flashscore.api.logic.domain.season.Season;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.Collection;

@Value
@AllArgsConstructor
@Jacksonized
@Builder
public class SeasonDTO {

    @NotNull
    @Valid
    private SeasonKeyDTO key;

    @Min(1950)
    @Max(2050)
    private int initYear;

    @Min(1950)
    @Max(2050)
    private int endYear;

    @NotNull
    @NotEmpty
    @Valid
    private Collection<MatchKeyDTO> matchesKeys;


    public SeasonDTO(Season season) {
        this.key = season.getKey() == null ? null : new SeasonKeyDTO(season.getKey());
        this.initYear = season.getInitYear();
        this.endYear = season.getEndYear();
        this.matchesKeys = season.getMatchesKeys() == null ? null : season.getMatchesKeys().stream().map(MatchKeyDTO::new).toList();
    }


    public Season toDomainEntity() {
        return new Season(
                key == null ? null : key.toDomainEntity(),
                initYear,
                endYear,
                matchesKeys == null ? null : matchesKeys.stream().map(MatchKeyDTO::toDomainEntity).toList()
        );
    }

}
package com.github.brunomndantas.flashscore.api.serviceInterface.dtos.season;

import com.github.brunomndantas.flashscore.api.logic.domain.season.Season;
import com.github.brunomndantas.flashscore.api.serviceInterface.dtos.match.MatchKeyDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.Collection;

@Value
@AllArgsConstructor
@Jacksonized
@Builder
@Schema(description = "Season")
public class SeasonDTO {

    @Schema(
            description = "Unique identifier for the season",
            example = "{\"sportId\": \"football\", \"regionId\": \"portugal\", \"competitionId\": \"liga-portugal\", \"seasonId\": \"2024-2025\"}")
    private SeasonKeyDTO key;

    @Schema(description = "Year in which the season started", example = "2024")
    private int initYear;

    @Schema(description = "Year in which the season finished", example = "2025")
    private int endYear;

    @Schema(description = "List of match keys for the season", example = "[{\"matchId\": \"thRumiAF\"}]")
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
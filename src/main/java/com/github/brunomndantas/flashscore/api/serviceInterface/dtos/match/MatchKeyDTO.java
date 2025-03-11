package com.github.brunomndantas.flashscore.api.serviceInterface.dtos.match;

import com.github.brunomndantas.flashscore.api.logic.domain.match.MatchKey;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@AllArgsConstructor
@Jacksonized
@Builder
@Schema(description = "Match key")
public class MatchKeyDTO {

    @Schema(description = "Unique identifier for the match", example = "thRumiAF")
    private String matchId;


    public MatchKeyDTO(MatchKey matchKey) {
        this.matchId = matchKey.getMatchId();
    }


    public MatchKey toDomainEntity() {
        return new MatchKey(matchId);
    }

}
package com.github.brunomndantas.flashscore.api.dataAccess.dtos.match;

import com.github.brunomndantas.flashscore.api.logic.domain.match.MatchKey;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@AllArgsConstructor
@Jacksonized
@Builder
public class MatchKeyDTO {

    @NotNull
    @NotEmpty
    @NotBlank
    private String matchId;


    public MatchKeyDTO(MatchKey matchKey) {
        this.matchId = matchKey.getMatchId();
    }


    public MatchKey toDomainEntity() {
        return new MatchKey(matchId);
    }

}
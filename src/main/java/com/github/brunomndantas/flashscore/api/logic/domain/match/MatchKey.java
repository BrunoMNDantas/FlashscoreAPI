package com.github.brunomndantas.flashscore.api.logic.domain.match;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MatchKey {

    @NotNull
    @NotEmpty
    @NotBlank
    @Schema(description = "Unique identifier for the match", example = "thRumiAF")
    private String matchId;

}
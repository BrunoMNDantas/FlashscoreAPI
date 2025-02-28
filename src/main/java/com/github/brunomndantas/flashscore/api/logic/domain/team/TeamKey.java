package com.github.brunomndantas.flashscore.api.logic.domain.team;

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
@Schema(description = "Team key")
public class TeamKey {

    @NotNull
    @NotEmpty
    @NotBlank
    @Schema(description = "Name identifier for the team", example = "sporting-cp")
    private String teamName;

    @NotNull
    @NotEmpty
    @NotBlank
    @Schema(description = "Unique identifier for the team", example = "tljXuHBC")
    private String teamId;

}
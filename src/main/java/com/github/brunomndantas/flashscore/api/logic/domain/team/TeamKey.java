package com.github.brunomndantas.flashscore.api.logic.domain.team;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TeamKey {

    @NotNull
    @NotEmpty
    @NotBlank
    private String teamName;

    @NotNull
    @NotEmpty
    @NotBlank
    private String teamId;

}
package com.github.brunomndantas.flashscore.api.logic.domain.player;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PlayerKey {

    @NotNull
    @NotEmpty
    @NotBlank
    private String playerName;

    @NotNull
    @NotEmpty
    @NotBlank
    private String playerId;

}
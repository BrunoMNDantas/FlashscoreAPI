package com.github.brunomndantas.flashscore.api.logic.domain.player;

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
public class PlayerKey {

    @NotNull
    @NotEmpty
    @NotBlank
    @Schema(description = "Name identifier for the player", example = "gyokeres-viktor")
    private String playerName;

    @NotNull
    @NotEmpty
    @NotBlank
    @Schema(description = "Unique identifier for the player", example = "zaBZ1xIk")
    private String playerId;

}
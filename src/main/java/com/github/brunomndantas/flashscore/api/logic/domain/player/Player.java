package com.github.brunomndantas.flashscore.api.logic.domain.player;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Player {

    @NotNull
    @Valid
    @Schema(description = "Unique identifier for the player", example = "{\"playerName\": \"gyokeres-viktor\",\"playerId\": \"zaBZ1xIk\"}")
    private PlayerKey key;

    @NotNull
    @NotEmpty
    @NotBlank
    @Schema(description = "Name of the player", example = "Viktor Gyokeres")
    private String name;

    @NotNull
    @Past
    @Schema(description = "Birthdate of the player", example = "1998-06-03T23:00:00.000+00:00")
    private Date birthDate;

    @NotNull
    @NotEmpty
    @NotBlank
    @Schema(description = "Role of the player", example = "Forward")
    private String role;

}
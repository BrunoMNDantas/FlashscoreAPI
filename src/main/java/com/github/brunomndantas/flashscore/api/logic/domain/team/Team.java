package com.github.brunomndantas.flashscore.api.logic.domain.team;

import com.github.brunomndantas.flashscore.api.logic.domain.player.PlayerKey;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Team {

    @NotNull
    @Valid
    private TeamKey key;

    @NotNull
    @NotEmpty
    @NotBlank
    private String name;

    @Size(min = 1, message = "Must be null or non-empty")
    private String stadium;

    @Min(-1)
    private int stadiumCapacity;

    @Valid
    private PlayerKey coachKey;

    @NotNull
    @Valid
    private Collection<PlayerKey> playersKeys;

}
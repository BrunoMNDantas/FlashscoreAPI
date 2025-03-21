package com.github.brunomndantas.flashscore.api.dataAccess.dtos.player;

import com.github.brunomndantas.flashscore.api.logic.domain.player.Player;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.Date;

@Value
@AllArgsConstructor
@Jacksonized
@Builder
public class PlayerDTO {

    @NotNull
    @Valid
    private PlayerKeyDTO key;

    @NotNull
    @NotEmpty
    @NotBlank
    private String name;

    @Past
    private Date birthDate;

    @NotNull
    @NotEmpty
    @NotBlank
    private String role;


    public PlayerDTO(Player player) {
        this.key = player.getKey() == null ? null : new PlayerKeyDTO(player.getKey());
        this.name = player.getName();
        this.birthDate = player.getBirthDate();
        this.role = player.getRole();
    }


    public Player toDomainEntity() {
        return new Player(
                key == null ? null : key.toDomainEntity(),
                name,
                birthDate,
                role
        );
    }

}
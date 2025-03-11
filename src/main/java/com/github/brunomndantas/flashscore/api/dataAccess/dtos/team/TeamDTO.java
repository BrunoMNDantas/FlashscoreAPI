package com.github.brunomndantas.flashscore.api.dataAccess.dtos.team;

import com.github.brunomndantas.flashscore.api.dataAccess.dtos.player.PlayerKeyDTO;
import com.github.brunomndantas.flashscore.api.logic.domain.team.Team;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.Collection;

@Value
@AllArgsConstructor
@Jacksonized
@Builder
public class TeamDTO {

    @NotNull
    @Valid
    private TeamKeyDTO key;

    @NotNull
    @NotEmpty
    @NotBlank
    private String name;

    @Size(min = 5, message = "Must be null or non-empty")
    private String stadium;

    @Min(-1)
    private int stadiumCapacity;

    @Valid
    private PlayerKeyDTO coachKey;

    @NotNull
    @NotEmpty
    @Valid
    private Collection<PlayerKeyDTO> playersKeys;


    public TeamDTO(Team team) {
        this.key = team.getKey() == null ? null : new TeamKeyDTO(team.getKey());
        this.name = team.getName();
        this.stadium = team.getStadium();
        this.stadiumCapacity = team.getStadiumCapacity();
        this.coachKey = team.getCoachKey() == null ? null : new PlayerKeyDTO(team.getCoachKey());
        this.playersKeys = team.getPlayersKeys() == null ? null : team.getPlayersKeys().stream().map(PlayerKeyDTO::new).toList();
    }


    public Team toDomainEntity() {
        return new Team(
                key == null ? null : key.toDomainEntity(),
                name,
                stadium,
                stadiumCapacity,
                coachKey == null ? null : coachKey.toDomainEntity(),
                playersKeys == null ? null : playersKeys.stream().map(PlayerKeyDTO::toDomainEntity).toList()
        );
    }

}
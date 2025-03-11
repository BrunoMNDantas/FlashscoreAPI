package com.github.brunomndantas.flashscore.api.serviceInterface.dtos.team;

import com.github.brunomndantas.flashscore.api.logic.domain.team.Team;
import com.github.brunomndantas.flashscore.api.serviceInterface.dtos.player.PlayerKeyDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.Collection;

@Value
@AllArgsConstructor
@Jacksonized
@Builder
@Schema(description = "Team")
public class TeamDTO {

    @Schema(description = "Unique identifier for the team", example = "{\"teamName\": \"sporting-lisbon\",\"teamId\": \"tljXuHBC\"}")
    private TeamKeyDTO key;

    @Schema(description = "Name of the team", example = "Sporting CP")
    private String name;

    @Schema(description = "Name of the team's stadium", example = "Estádio José Alvalade (Lisbon)")
    private String stadium;

    @Schema(description = "Capacity of the team's stadium", example = "50466")
    private int stadiumCapacity;

    @Schema(description = "Unique identifier for team's coach", example = "{\"playerName\": \"amorim-ruben\",\"playerId\": \"EiIHi8vU\"}")
    private PlayerKeyDTO coachKey;

    @Schema(description = "List of player keys for the team", example = "[{\"playerName\": \"gyokeres-viktor\",\"playerId\": \"zaBZ1xIk\"}]")
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
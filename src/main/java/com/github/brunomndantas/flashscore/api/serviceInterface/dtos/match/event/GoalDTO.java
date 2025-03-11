package com.github.brunomndantas.flashscore.api.serviceInterface.dtos.match.event;

import com.github.brunomndantas.flashscore.api.logic.domain.match.event.Goal;
import com.github.brunomndantas.flashscore.api.serviceInterface.dtos.player.PlayerKeyDTO;
import com.github.brunomndantas.flashscore.api.serviceInterface.dtos.team.TeamKeyDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@AllArgsConstructor
@Jacksonized
@Builder
@Schema(description = "Goal")
public class GoalDTO {

    @Schema(description = "Time in which the event occurred", example = "{\"period\": \"FIRST_HALF\", \"minute\": 29, \"extraMinute\": 0}")
    private TimeDTO time;

    @Schema(description = "Unique identifier for the team", example = "{\"teamName\": \"sporting-lisbon\",\"teamId\": \"tljXuHBC\"}")
    private TeamKeyDTO teamKey;

    @Schema(description = "Unique identifier for the player", example = "{\"playerName\":\"catamo-geny\", \"playerId\":\"29eth6dF\"")
    private PlayerKeyDTO playerKey;

    @Schema(description = "Unique identifier for the assist player", example = "{\"playerName\":\"gyokeres-viktor\", \"playerId\":\"zaBZ1xIk\"}")
    private PlayerKeyDTO assistPlayerKey;


    public GoalDTO(Goal goal) {
        this.time = goal.getTime() == null ? null : new TimeDTO(goal.getTime());
        this.teamKey = goal.getTeamKey() == null ? null : new TeamKeyDTO(goal.getTeamKey());
        this.playerKey = goal.getPlayerKey() == null ? null : new PlayerKeyDTO(goal.getPlayerKey());
        this.assistPlayerKey = goal.getAssistPlayerKey() == null ? null : new PlayerKeyDTO(goal.getAssistPlayerKey());
    }


    public Goal toDomainEntity() {
        return new Goal(
                time == null ? null : time.toDomainEntity(),
                teamKey == null ? null : teamKey.toDomainEntity(),
                playerKey == null ? null : playerKey.toDomainEntity(),
                assistPlayerKey == null ? null : assistPlayerKey.toDomainEntity()
        );
    }

}
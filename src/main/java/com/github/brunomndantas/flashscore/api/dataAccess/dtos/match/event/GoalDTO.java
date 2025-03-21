package com.github.brunomndantas.flashscore.api.dataAccess.dtos.match.event;

import com.github.brunomndantas.flashscore.api.dataAccess.dtos.player.PlayerKeyDTO;
import com.github.brunomndantas.flashscore.api.dataAccess.dtos.team.TeamKeyDTO;
import com.github.brunomndantas.flashscore.api.logic.domain.match.event.Goal;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@AllArgsConstructor
@Jacksonized
@Builder
public class GoalDTO {

    @NotNull
    @Valid
    private TimeDTO time;

    @NotNull
    @Valid
    private TeamKeyDTO teamKey;

    @Valid
    private PlayerKeyDTO playerKey;

    @Valid
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
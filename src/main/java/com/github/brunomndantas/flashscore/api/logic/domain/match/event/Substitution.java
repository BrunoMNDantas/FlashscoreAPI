package com.github.brunomndantas.flashscore.api.logic.domain.match.event;

import com.github.brunomndantas.flashscore.api.logic.domain.player.PlayerKey;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Embeddable
@AttributeOverrides({
        @AttributeOverride(name = "type", column = @Column(name = "substitution_type")),
        @AttributeOverride(name = "minute", column = @Column(name = "substitution_minute")),
        @AttributeOverride(name = "extraMinute", column = @Column(name = "substitution_extra_minute")),
        @AttributeOverride(name = "team_id", column = @Column(name = "substitution_team_id")),
        @AttributeOverride(name = "team_name", column = @Column(name = "substitution_team_name"))
})
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@Schema(description = "Substitution")
public class Substitution extends Event {

    @NotNull
    @Valid
    @Schema(description = "Unique identifier for the player entering", example = "{\"playerName\": \"simoes-joao\",\"playerId\": \"4UYkqiMB\"},")
    private PlayerKey inPlayerKey;

    @NotNull
    @Valid
    @Schema(description = "Unique identifier for the player leaving", example = "{\"playerName\": \"morita-hidemasa\",\"playerId\": \"Y3AIyFGA\"}")
    private PlayerKey outPlayerKey;

}
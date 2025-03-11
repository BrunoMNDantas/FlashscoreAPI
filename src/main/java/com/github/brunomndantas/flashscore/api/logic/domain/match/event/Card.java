package com.github.brunomndantas.flashscore.api.logic.domain.match.event;

import com.github.brunomndantas.flashscore.api.logic.domain.player.PlayerKey;
import com.github.brunomndantas.flashscore.api.logic.domain.team.TeamKey;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
@Schema(description = "Card")
public class Card extends Event {

    @Schema(description = "Color")
    public enum Color {
        YELLOW,
        RED
    }


    @NotNull
    @Valid
    @Schema(description = "Unique identifier for the player", example = "{\"playerName\": \"bah-alexander\", \"playerId\": \"Kn1rziON\"}")
    private PlayerKey playerKey;

    @NotNull
    @Schema(description = "Color of the card", example = "YELLOW")
    private Color color;


    public Card(Time time, TeamKey teamKey, PlayerKey playerKey, Color color) {
        super(time, teamKey);
        this.playerKey = playerKey;
        this.color = color;
    }

}
package com.github.brunomndantas.flashscore.api.logic.domain.match.event;

import com.github.brunomndantas.flashscore.api.logic.domain.player.PlayerKey;
import com.github.brunomndantas.flashscore.api.logic.domain.team.TeamKey;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
public class Card extends Event {

    public enum Color {
        YELLOW,
        RED
    }


    @NotNull
    @Valid
    private PlayerKey playerKey;

    @NotNull
    private Color color;


    public Card(Time time, TeamKey teamKey, PlayerKey playerKey, Color color) {
        super(time, teamKey);
        this.playerKey = playerKey;
        this.color = color;
    }

}
package com.github.brunomndantas.flashscore.api.logic.domain.match.event;

import com.github.brunomndantas.flashscore.api.logic.domain.team.TeamKey;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Event {

    private Time time;

    private TeamKey teamKey;

}
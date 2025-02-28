package com.github.brunomndantas.flashscore.api.logic.domain.match.event;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Event type")
public enum EventType {

    GOAL,
    SUBSTITUTION,
    CARD,
    PENALTY

}
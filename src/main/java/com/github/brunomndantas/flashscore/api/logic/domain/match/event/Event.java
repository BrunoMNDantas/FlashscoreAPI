package com.github.brunomndantas.flashscore.api.logic.domain.match.event;

import com.github.brunomndantas.flashscore.api.logic.domain.team.TeamKey;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Schema(oneOf = {Card.class, Goal.class, Substitution.class, Penalty.class})
public class Event {

    @NotNull
    @Schema(description = "Type of the event", example = "GOAL")
    private EventType type;

    @Min(0)
    @Max(150)
    @Schema(description = "Minute in which the event occurred", example = "29")
    private int minute;

    @Min(0)
    @Max(30)
    @Schema(description = "Extra minute in which the event occurred", example = "0")
    private int extraMinute;

    @NotNull
    @Valid
    @Schema(description = "Unique identifier for the team", example = "{\"teamName\": \"sporting-lisbon\",\"teamId\": \"tljXuHBC\"}")
    private TeamKey teamKey;

}
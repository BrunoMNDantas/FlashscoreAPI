package com.github.brunomndantas.flashscore.api.logic.domain.match.event;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.github.brunomndantas.flashscore.api.logic.domain.team.TeamKey;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Schema(description= "Event", oneOf = {Card.class, Goal.class, Substitution.class, Penalty.class})
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Goal.class, name = "GOAL"),
        @JsonSubTypes.Type(value = Card.class, name = "CARD"),
        @JsonSubTypes.Type(value = Substitution.class, name = "SUBSTITUTION"),
        @JsonSubTypes.Type(value = Penalty.class, name = "PENALTY")
})
public class Event {

    @NotNull
    @Valid
    @Schema(description = "Time in which the event occurred", example = "{\"period\": \"SECOND_HALF\", \"minute\": 72, \"extraMinute\": 0}")
    private Time time;

    @NotNull
    @Valid
    @Schema(description = "Unique identifier for the team", example = "{\"teamName\": \"sporting-lisbon\",\"teamId\": \"tljXuHBC\"}")
    private TeamKey teamKey;

}
package com.github.brunomndantas.flashscore.api.logic.domain.match.event;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.github.brunomndantas.flashscore.api.logic.domain.team.TeamKey;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
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
    private Time time;

    @NotNull
    @Valid
    private TeamKey teamKey;

}
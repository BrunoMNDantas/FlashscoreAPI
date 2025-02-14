package com.github.brunomndantas.flashscore.api.logic.domain.team;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Team {

    @NotNull
    @Valid
    private TeamId id;

    @NotNull
    @NotEmpty
    @NotBlank
    private String name;

    @NotNull
    @NotEmpty
    @NotBlank
    private String coachId;

    @NotNull
    @NotEmpty
    @Valid
    private Collection<
        @NotEmpty
        @NotNull
        @NotBlank
        String
    > playersIds;

}

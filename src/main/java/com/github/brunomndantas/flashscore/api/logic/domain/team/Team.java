package com.github.brunomndantas.flashscore.api.logic.domain.team;

import com.github.brunomndantas.flashscore.api.logic.domain.player.PlayerId;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Collection;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Team {

    @NotNull
    @Valid
    private TeamId id;

    @NotNull
    @NotEmpty
    @NotBlank
    private String name;

    @NotNull
    @Valid
    private PlayerId coachId;

    @NotNull
    @NotEmpty
    @Valid
    private Collection<
        @NotEmpty
        @Valid
        PlayerId
    > playersIds;

}

package com.github.brunomndantas.flashscore.api.logic.domain.team;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class TeamId {

    @NotNull
    @NotEmpty
    @NotBlank
    private String id;

}

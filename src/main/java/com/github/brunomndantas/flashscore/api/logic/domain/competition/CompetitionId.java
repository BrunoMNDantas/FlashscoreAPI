package com.github.brunomndantas.flashscore.api.logic.domain.competition;

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
public class CompetitionId {

    @NotNull
    @NotEmpty
    @NotBlank
    private String sportId;

    @NotNull
    @NotEmpty
    @NotBlank
    private String regionId;

    @NotNull
    @NotEmpty
    @NotBlank
    private String id;

}

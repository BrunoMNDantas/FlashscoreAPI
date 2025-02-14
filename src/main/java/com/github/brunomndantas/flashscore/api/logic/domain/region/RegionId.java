package com.github.brunomndantas.flashscore.api.logic.domain.region;

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
public class RegionId {

    @NotNull
    @NotEmpty
    @NotBlank
    private String sportId;

    @NotNull
    @NotEmpty
    @NotBlank
    private String id;

}

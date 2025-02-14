package com.github.brunomndantas.flashscore.api.logic.domain.player;

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
public class PlayerId {

    @NotNull
    @NotEmpty
    @NotBlank
    private String id;

}

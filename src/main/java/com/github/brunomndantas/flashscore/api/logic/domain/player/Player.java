package com.github.brunomndantas.flashscore.api.logic.domain.player;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Player {

    @NotNull
    @Valid
    private PlayerKey key;

    @NotNull
    @NotEmpty
    @NotBlank
    private String name;

    @NotNull
    @Past
    private Date birthDate;

    @NotNull
    @NotEmpty
    @NotBlank
    private String role;

}

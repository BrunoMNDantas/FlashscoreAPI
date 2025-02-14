package com.github.brunomndantas.flashscore.api.logic.domain.player;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Player {

    @NotNull
    @Valid
    private PlayerId id;

    @NotNull
    @NotEmpty
    @NotBlank
    private String name;

    @NotNull
    @Past
    private Date birthDate;

}

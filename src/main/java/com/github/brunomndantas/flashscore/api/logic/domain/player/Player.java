package com.github.brunomndantas.flashscore.api.logic.domain.player;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Player {

    private PlayerKey key;

    private String name;

    private Date birthDate;

    private String role;

}
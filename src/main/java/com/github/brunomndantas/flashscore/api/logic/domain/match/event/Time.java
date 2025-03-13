package com.github.brunomndantas.flashscore.api.logic.domain.match.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Time {

    public enum Period {

        FIRST_HALF,
        SECOND_HALF,
        EXTRA_TIME,
        PENALTIES

    }


    private Period period;

    private int minute;

    private int extraMinute;

}
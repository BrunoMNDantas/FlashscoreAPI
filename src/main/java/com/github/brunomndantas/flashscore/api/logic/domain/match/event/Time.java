package com.github.brunomndantas.flashscore.api.logic.domain.match.event;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
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

    @NotNull
    private Period period;

    @Min(0)
    private int minute;

    @Min(0)
    private int extraMinute;

}
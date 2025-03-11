package com.github.brunomndantas.flashscore.api.logic.domain.match.event;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Schema(description = "Time")
public class Time {

    @Schema(description = "Period")
    public enum Period {

        FIRST_HALF,
        SECOND_HALF,
        EXTRA_TIME,
        PENALTIES

    }


    @Schema(description = "Period of the match in which the event occurred", example = "FIRST_HALF")
    @NotNull
    private Period period;

    @Schema(description = "Minute in which the event occurred", example = "29")
    @Min(0)
    private int minute;

    @Schema(description = "Extra minute in which the event occurred", example = "0")
    @Min(0)
    private int extraMinute;

}
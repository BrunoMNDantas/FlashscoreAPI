package com.github.brunomndantas.flashscore.api.serviceInterface.dtos.match.event;

import com.github.brunomndantas.flashscore.api.logic.domain.match.event.Time;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@AllArgsConstructor
@Jacksonized
@Builder
@Schema(description = "Time")
public class TimeDTO {

    @Schema(description = "Period of the match")
    public enum PeriodDTO {

        FIRST_HALF,
        SECOND_HALF,
        EXTRA_TIME,
        PENALTIES

    }


    @Schema(description = "Period of the match in which the event occurred", example = "FIRST_HALF")
    private PeriodDTO period;

    @Schema(description = "Minute in which the event occurred", example = "29")
    private int minute;

    @Schema(description = "Extra minute in which the event occurred", example = "0")
    private int extraMinute;


    public TimeDTO(Time time) {
        this.period = time.getPeriod() == null ? null : PeriodDTO.valueOf(time.getPeriod().toString());
        this.minute = time.getMinute();
        this.extraMinute = time.getExtraMinute();
    }


    public Time toDomainEntity() {
        return new Time(
                period == null ? null : Time.Period.valueOf(period.toString()),
                minute,
                extraMinute
        );
    }

}
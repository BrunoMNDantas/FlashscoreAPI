package com.github.brunomndantas.flashscore.api.dataAccess.dtos.match.event;

import com.github.brunomndantas.flashscore.api.logic.domain.match.event.Time;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@AllArgsConstructor
@Jacksonized
@Builder
public class TimeDTO {

    public enum PeriodDTO {

        FIRST_HALF,
        SECOND_HALF,
        EXTRA_TIME,
        PENALTIES

    }

    @NotNull
    private PeriodDTO period;

    @Min(0)
    private int minute;

    @Min(0)
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
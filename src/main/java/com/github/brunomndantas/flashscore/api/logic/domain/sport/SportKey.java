package com.github.brunomndantas.flashscore.api.logic.domain.sport;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Data
@Schema(description = "Sport key")
public class SportKey {

    @NotNull
    @NotEmpty
    @NotBlank
    @Schema(description = "Unique identifier for the sport", example = "football")
    private String sportId;

}
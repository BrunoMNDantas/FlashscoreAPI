package com.github.brunomndantas.flashscore.api.logic.domain.season;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SeasonKey {

    @NotNull
    @NotEmpty
    @NotBlank
    private String sportId;

    @NotNull
    @NotEmpty
    @NotBlank
    private String regionId;

    @NotNull
    @NotEmpty
    @NotBlank
    private String competitionId;

    @NotNull
    @NotEmpty
    @NotBlank
    @Pattern(regexp = "^(19\\d{2}|20\\d{2})(?:-(19\\d{2}|20\\d{2}))?$", message = "Invalid season format. Must be 'YYYY' or 'YYYY-YYYY' with years ≥ 1900.")
    private String seasonId;

}

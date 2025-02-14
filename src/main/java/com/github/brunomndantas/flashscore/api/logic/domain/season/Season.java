package com.github.brunomndantas.flashscore.api.logic.domain.season;

import com.github.brunomndantas.flashscore.api.logic.domain.match.MatchKey;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.Collection;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Season {

    @NotNull
    @Valid
    private SeasonKey key;

    @NotNull
    @NotEmpty
    @NotBlank
    @Pattern(regexp = "^(19\\d{2}|20\\d{2})(?:-(19\\d{2}|20\\d{2}))?$", message = "Invalid season format. Must be 'YYYY' or 'YYYY-YYYY' with years ≥ 1900.")
    private String name;

    @NotNull
    @NotEmpty
    @Valid
    private Collection<MatchKey> matchesKeys;

}

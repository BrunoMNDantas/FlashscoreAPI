package com.github.brunomndantas.flashscore.api.serviceInterface.controllers;

import com.github.brunomndantas.flashscore.api.logic.domain.sport.Sport;
import com.github.brunomndantas.flashscore.api.logic.domain.sport.SportKey;
import com.github.brunomndantas.flashscore.api.serviceInterface.config.Routes;
import com.github.brunomndantas.flashscore.api.serviceInterface.dtos.sport.SportDTO;
import com.github.brunomndantas.repository4j.IRepository;
import com.github.brunomndantas.repository4j.exception.RepositoryException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "01. Sports", description = "Operations related to Sports")
@RestController
public class SportController {

    private IRepository<SportKey,Sport> repository;


    public SportController(IRepository<SportKey, Sport> repository) {
        this.repository = repository;
    }


    @GetMapping(Routes.SPORT_ROUTE)
    @Operation(summary = "Get sport by key", description = "Returns the sport with the given key if found, otherwise returns 404")
    @ApiResponse(responseCode = "200", description = "Sport found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Sport.class)))
    @ApiResponse(responseCode = "404", description = "Sport not found", content = @Content(mediaType = "text/plain"))
    public SportDTO getSport(@PathVariable String sportId) throws RepositoryException {
        Sport sport = repository.get(new SportKey(sportId));
        return sport == null ? null : new SportDTO(sport);
    }

}

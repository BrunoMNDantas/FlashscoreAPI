package com.github.brunomndantas.flashscore.api.serviceInterface.controllers;

import com.github.brunomndantas.flashscore.api.logic.domain.region.Region;
import com.github.brunomndantas.flashscore.api.logic.domain.region.RegionKey;
import com.github.brunomndantas.flashscore.api.serviceInterface.config.Routes;
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

@Tag(name = "02. Regions", description = "Operations related to Regions")
@RestController
public class RegionController {

    private IRepository<RegionKey, Region> repository;


    public RegionController(IRepository<RegionKey, Region> repository) {
        this.repository = repository;
    }


    @GetMapping(Routes.REGION_ROUTE)
    @Operation(summary = "Get region by key", description = "Returns the region with the given key if found, otherwise returns 404")
    @ApiResponse(responseCode = "200", description = "Region found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Region.class)))
    @ApiResponse(responseCode = "404", description = "Region not found", content = @Content(mediaType = "text/plain"))
    public Region getRegion(@PathVariable String sportId, @PathVariable String regionId) throws RepositoryException {
        return repository.get(new RegionKey(sportId, regionId));
    }

}

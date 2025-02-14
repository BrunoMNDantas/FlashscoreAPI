package com.github.brunomndantas.flashscore.api.serviceInterface.controllers;

import com.github.brunomndantas.flashscore.api.logic.domain.region.Region;
import com.github.brunomndantas.flashscore.api.logic.domain.region.RegionKey;
import com.github.brunomndantas.flashscore.api.serviceInterface.config.Routes;
import com.github.brunomndantas.repository4j.IRepository;
import com.github.brunomndantas.repository4j.exception.RepositoryException;
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
    public Region getRegion(@PathVariable String sportId, @PathVariable String regionId) throws RepositoryException {
        return repository.get(new RegionKey(sportId, regionId));
    }

}

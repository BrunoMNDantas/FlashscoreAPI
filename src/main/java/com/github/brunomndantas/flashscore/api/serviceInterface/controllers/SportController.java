package com.github.brunomndantas.flashscore.api.serviceInterface.controllers;

import com.github.brunomndantas.flashscore.api.logic.domain.sport.Sport;
import com.github.brunomndantas.flashscore.api.serviceInterface.config.Routes;
import com.github.brunomndantas.repository4j.IRepository;
import com.github.brunomndantas.repository4j.exception.RepositoryException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SportController {

    private IRepository<String,Sport> repository;


    public SportController(IRepository<String, Sport> repository) {
        this.repository = repository;
    }


    @GetMapping(Routes.SPORT_ROUTE)
    public Sport getSport(@PathVariable String sportId) throws RepositoryException {
        return repository.get(sportId);
    }

}

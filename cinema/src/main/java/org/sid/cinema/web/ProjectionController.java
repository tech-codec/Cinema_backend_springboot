package org.sid.cinema.web;

import org.sid.cinema.entities.Projection;
import org.sid.cinema.service.ProjectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@CrossOrigin("*")
@RestController
public class ProjectionController {
    @Autowired
    private ProjectionService projectionService;
    @PostMapping("/addProjectionToSalleAndFilmAndSeance/{idSalle}/{idFilm}/{idSeance}")
    Projection addProjectionToSalleAndFilmAndSeance(@RequestBody Projection projection, @PathVariable Long idSalle, @PathVariable Long idFilm, @PathVariable Long idSeance){
        return projectionService.addProjectionToSalleAndFilmAndSeance(projection, idSalle, idFilm, idSeance);
    }

    @PostMapping("/addProjectionToFilm/{idFilm}")
    Projection addProjectionToFilm(@RequestBody Projection projection, @PathVariable Long idFilm){
        return projectionService.addProjectionToFilm(projection, idFilm);
    }
}

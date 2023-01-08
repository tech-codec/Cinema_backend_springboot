package org.sid.cinema.service;

import org.sid.cinema.entities.Projection;


public interface ProjectionService {
    Projection addProjectionToSalle(Projection projection, Long idSalle);
    Projection addProjectionToFilm(Projection projection, Long idFilm);
    Projection addProjectionToSeance(Projection projection, Long idSeance);
    Projection addProjectionToSalleAndFilmAndSeance(Projection projection,Long idSalle,Long idFilm,Long idSeance);
}

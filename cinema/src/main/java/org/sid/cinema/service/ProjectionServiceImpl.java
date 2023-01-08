package org.sid.cinema.service;

import org.sid.cinema.dao.FilmRepository;
import org.sid.cinema.dao.ProjectionRepository;
import org.sid.cinema.dao.SalleRepository;
import org.sid.cinema.dao.SeanceRepository;
import org.sid.cinema.entities.Film;
import org.sid.cinema.entities.Projection;
import org.sid.cinema.entities.Salle;
import org.sid.cinema.entities.Seance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Transactional
public class ProjectionServiceImpl implements ProjectionService {
    @Autowired
    private SalleRepository salleRepository;
    @Autowired
    private FilmRepository filmRepository;
    @Autowired
    private ProjectionRepository projectionRepository;
    @Autowired
    private SeanceRepository seanceRepository;
    @Override
    public Projection addProjectionToSalle(Projection projection, Long idSalle) {
        Salle salle = salleRepository.findById(idSalle).orElse(null);
        salle.addProjectionToSalle(projection);
        return projectionRepository.save(projection);
    }

    @Override
    public Projection addProjectionToFilm(Projection projection, Long idFilm) {
        Film film = filmRepository.findById(idFilm).orElse(null);
        film.addProjectionToFilm(projection);
        return projectionRepository.save(projection);
    }

    @Override
    public Projection addProjectionToSeance(Projection projection, Long idSeance) {
        Seance seance = seanceRepository.findById(idSeance).orElse(null);
        seance.addProjectionToSeance(projection);
        return projectionRepository.save(projection);
    }

    @Override
    public Projection addProjectionToSalleAndFilmAndSeance(Projection projection, Long idSalle, Long idFilm, Long idSeance) {
        Salle salle = salleRepository.findById(idSalle).orElse(null);
        Film film = filmRepository.findById(idFilm).orElse(null);
        Seance seance = seanceRepository.findById(idSeance).orElse(null);
        salle.addProjectionToSalle(projection);
        film.addProjectionToFilm(projection);
        seance.addProjectionToSeance(projection);
        projection.setDateProjection(new Date());
        return projectionRepository.save(projection);
    }
}

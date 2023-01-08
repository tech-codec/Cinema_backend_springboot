package org.sid.cinema.service;

import org.sid.cinema.entities.Cinema;

public interface CinemaService {
    Cinema addCinemaToVille(Cinema cinema, Long idVille);
}

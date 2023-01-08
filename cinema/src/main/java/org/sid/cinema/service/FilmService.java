package org.sid.cinema.service;

import org.sid.cinema.entities.Film;

public interface FilmService {
    Film addFilmToCategorie(Film film, Long idCategorie);
}

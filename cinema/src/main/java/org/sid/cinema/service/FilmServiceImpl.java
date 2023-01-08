package org.sid.cinema.service;

import org.sid.cinema.dao.CategorieRepository;
import org.sid.cinema.dao.FilmRepository;
import org.sid.cinema.entities.Categorie;
import org.sid.cinema.entities.Film;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FilmServiceImpl implements FilmService {
    @Autowired
    private CategorieRepository categorieRepository;
    @Autowired
    private FilmRepository filmRepository;
    @Override
    public Film addFilmToCategorie(Film film, Long idCategorie) {
        Categorie categorie = categorieRepository.findById(idCategorie).orElse(null);
        categorie.addFilmToCategorie(film);
        return filmRepository.save(film);
    }
}

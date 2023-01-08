package org.sid.cinema.service;

import org.sid.cinema.dao.CinemaRepository;
import org.sid.cinema.dao.VilleRepository;
import org.sid.cinema.entities.Cinema;
import org.sid.cinema.entities.Ville;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CinemaServiceImpl implements CinemaService {

    @Autowired
    private VilleRepository villeRepository;

    @Autowired
    private CinemaRepository cinemaRepository;

    @Override
    public Cinema addCinemaToVille(Cinema cinema, Long idVille) {
        Ville ville = villeRepository.findById(idVille).orElse(null);
        ville.addCinemaToVille(cinema);
        return cinemaRepository.save(cinema);
    }

}

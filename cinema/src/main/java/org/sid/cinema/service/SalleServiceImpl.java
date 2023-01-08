package org.sid.cinema.service;

import org.sid.cinema.dao.CinemaRepository;
import org.sid.cinema.dao.SalleRepository;
import org.sid.cinema.entities.Cinema;
import org.sid.cinema.entities.Salle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SalleServiceImpl implements SalleService {

    @Autowired
    CinemaRepository cinemaRepository;
    @Autowired
    SalleRepository salleRepository;
    @Override
    public Salle addSalleToCinema(Salle salle, Long idCinema) {
        Cinema cinema = cinemaRepository.findById(idCinema).orElse(null);
        cinema.addSalleToCinema(salle);
        return salleRepository.save(salle);
    }
}

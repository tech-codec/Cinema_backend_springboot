package org.sid.cinema.service;

import org.sid.cinema.dao.PlaceRepository;
import org.sid.cinema.dao.SalleRepository;
import org.sid.cinema.entities.Place;
import org.sid.cinema.entities.Salle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PlaceServiceImpl implements PlaceService{
    @Autowired
    private SalleRepository salleRepository;
    @Autowired
    private PlaceRepository placeRepository;
    @Override
    public Place addPlaceToSalle(Place place, Long idSalle) {
        Salle salle = salleRepository.findById(idSalle).orElse(null);
        salle.addPlaceToSalle(place);
        return placeRepository.save(place);
    }
}

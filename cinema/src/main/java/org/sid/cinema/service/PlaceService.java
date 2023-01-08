package org.sid.cinema.service;

import org.sid.cinema.entities.Place;

public interface PlaceService {
    Place addPlaceToSalle(Place place, Long idSalle);
}

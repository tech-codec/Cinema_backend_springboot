package org.sid.cinema.service;

import org.sid.cinema.entities.Salle;

public interface SalleService {
    Salle addSalleToCinema(Salle salle, Long idCinema);
}

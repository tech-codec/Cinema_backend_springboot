package org.sid.cinema.service;

import org.sid.cinema.entities.Ticket;

public interface TicketService {
    Ticket addTicketToPlace(Ticket ticket, Long idPlace);
    Ticket addTicketToProjection(Ticket ticket, Long idProjection);
    Ticket addTicketToProjectionAndPlace(Ticket ticket,Long idPlace,Long idProjection);
}

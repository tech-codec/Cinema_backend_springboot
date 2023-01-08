package org.sid.cinema.service;

import org.sid.cinema.dao.PlaceRepository;
import org.sid.cinema.dao.ProjectionRepository;
import org.sid.cinema.dao.TicketRepository;
import org.sid.cinema.entities.Place;
import org.sid.cinema.entities.Projection;
import org.sid.cinema.entities.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TicketServiceImpl implements TicketService {

    @Autowired
    private PlaceRepository placeRepository;
    @Autowired
    private ProjectionRepository projectionRepository;
    @Autowired
    private TicketRepository ticketRepository;
    @Override
    public Ticket addTicketToPlace(Ticket ticket, Long idPlace) {
        Place place = placeRepository.findById(idPlace).orElse(null);
        place.addTicketToPlace(ticket);
        return ticketRepository.save(ticket);
    }

    @Override
    public Ticket addTicketToProjection(Ticket ticket, Long idProjection) {
        Projection projection = projectionRepository.findById(idProjection).orElse(null);
        projection.addTicketToProjection(ticket);
        return ticketRepository.save(ticket);
    }

    @Override
    public Ticket addTicketToProjectionAndPlace(Ticket ticket, Long idPlace, Long idProjection) {
        ticket.setReservee(false);
        Place place = placeRepository.findById(idPlace).orElse(null);
        Projection projection = projectionRepository.findById(idProjection).orElse(null);
        place.addTicketToPlace(ticket);
        projection.addTicketToProjection(ticket);
        ticket.setPrix(projection.getPrix());
        return ticketRepository.save(ticket);

    }
}

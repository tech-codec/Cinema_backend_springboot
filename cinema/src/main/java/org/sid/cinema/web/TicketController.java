package org.sid.cinema.web;

import org.sid.cinema.entities.Ticket;
import org.sid.cinema.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
public class TicketController {
    @Autowired
    private TicketService ticketService;

    @PostMapping("/addTicketToProjectionAndPlace/{idProjection}/{idPlace}")
    Ticket addTicketToProjectionAndPlace(@RequestBody Ticket ticket, @PathVariable Long idProjection, @PathVariable Long idPlace){
        ticketService.addTicketToProjection(ticket, idProjection);
        return ticketService.addTicketToPlace(ticket, idPlace);
    }

    @PostMapping("/addTicketToPlace/{idPlace}")
    Ticket addTicketToPlace(@RequestBody Ticket ticket, @PathVariable Long idPlace){
        return ticketService.addTicketToPlace(ticket, idPlace);
    }
}

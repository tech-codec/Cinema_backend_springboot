package org.sid.cinema.web;

import lombok.Data;
import org.sid.cinema.dao.FilmRepository;
import org.sid.cinema.dao.TicketRepository;
import org.sid.cinema.entities.Film;
import org.sid.cinema.entities.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin("*")
@RestController
public class CinemaRestController {

    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private FilmRepository filmRepository;
    @GetMapping(path = "/imageFilm/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] image(@PathVariable(name ="id") Long id) throws Exception{
        Film f = filmRepository.findById(id).get();
        String photoName = f.getPhoto();
        File file = new File(System.getProperty("user.home")+"/cinema/images/"+photoName);
        Path path = Paths.get(file.toURI());
        return Files.readAllBytes(path);
    }

    /*avec cette methode nous allons recuperer le identifiant du ticket au niveau du formulaire avec la @PostMapping*/
    @PostMapping("/payerTickets")
    @Transactional
    public List<Ticket> payerTickets(@RequestBody TicketForm ticketForm){
        List<Ticket> listTickets = new ArrayList<>();
        ticketForm.getTickets().forEach(idTicket->{
            System.out.println(idTicket);
            Ticket ticket = ticketRepository.findById(idTicket).get();
            ticket.setNomClient(ticketForm.getNameClient());
            ticket.setCodePayement(ticketForm.getCodePayement());
            ticket.setReservee(true);
            ticketRepository.save(ticket);
            listTickets.add(ticket);
        });
        return listTickets;
    }

    @PostMapping(path ="/uploadPhoto/{id}")
    public void uploadPhoto(MultipartFile file, @PathVariable Long id)throws Exception{
        Film f = filmRepository.findById(id).get();
        f.setPhoto(file.getOriginalFilename());
        Files.write(Paths.get(System.getProperty("user.home")+"/cinema/images/"+ f.getPhoto()),file.getBytes());
        filmRepository.save(f);

    }


}


/*cette classe defini les variable ou serons stocke les donnees fournir par le formulaire (notre model)*/
@Data
class TicketForm{
    private String nameClient;
    private int codePayement;
    private List<Long> tickets = new ArrayList<>();
}

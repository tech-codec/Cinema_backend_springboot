package org.sid.cinema.web;

import org.sid.cinema.entities.Salle;
import org.sid.cinema.service.SalleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
public class SalleController {

    @Autowired
    private SalleService salleService;

    @PostMapping("/addSalleToCinema/{idCinema}")
    Salle addSalleToCinema(@RequestBody Salle salle, @PathVariable Long idCinema){
        return salleService.addSalleToCinema(salle, idCinema);
    }

}

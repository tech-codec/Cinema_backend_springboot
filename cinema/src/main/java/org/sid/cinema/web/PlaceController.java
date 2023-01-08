package org.sid.cinema.web;

import org.sid.cinema.entities.Place;
import org.sid.cinema.service.PlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
public class PlaceController {
    @Autowired
    private PlaceService placeService;
    @PostMapping("/addPlaceTosalle/{idSalle}")
    Place addPlaceToSalle(@RequestBody Place place, @PathVariable Long idSalle){
        return placeService.addPlaceToSalle(place, idSalle);
    }
}

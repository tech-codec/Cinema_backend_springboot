package org.sid.cinema.web;

import org.sid.cinema.dao.CinemaRepository;
import org.sid.cinema.entities.Cinema;
import org.sid.cinema.service.CinemaService;
import org.sid.cinema.util.PdfCinema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@CrossOrigin("*")
@RestController
public class CinemaController {

    @Autowired
    private CinemaRepository cinemaRepository;

    @Autowired
    private CinemaService cinemaService;
    @PostMapping(path = "/addCinemaToVille/{idVille}")
    Cinema addCinemaToVille(@RequestBody Cinema cinema, @PathVariable Long idVille){
        return cinemaService.addCinemaToVille(cinema, idVille);
    }

    @GetMapping(path = "/PDFcinema", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity cinemaRapport() throws IOException {
        List cinemas =  cinemaRepository.findAll();
        ByteArrayInputStream bis = PdfCinema.cinemaPDF(cinemas);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=cinemas.pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }
}

package org.sid.cinema.web;

import org.sid.cinema.dao.FilmRepository;
import org.sid.cinema.entities.Film;
import org.sid.cinema.service.FilmService;
import org.sid.cinema.util.PDFFilm;
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
public class FilmController {
    @Autowired
    private FilmService filmService;

    @Autowired
    private FilmRepository filmRepository;

    @PostMapping("/addFilmToCategorie/{idCategorie}")
    Film addFilmToCategorie(@RequestBody Film film, @PathVariable Long idCategorie){
        return filmService.addFilmToCategorie(film, idCategorie);
    }

    @GetMapping(path = "/PDFfilms", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity filmsRapport() throws IOException {
        List films =  filmRepository.findAll();
        ByteArrayInputStream bis = PDFFilm.lisFilmsPDF(films);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=ListFilms.pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }


    @GetMapping(path = "/PDFfilm/{id}", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity filmRapport(@PathVariable(name ="id") Long id) throws IOException {
        Film film = filmRepository.findById(id).get();
        ByteArrayInputStream bis = PDFFilm.FilmPDF(film);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=Film.pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }
}

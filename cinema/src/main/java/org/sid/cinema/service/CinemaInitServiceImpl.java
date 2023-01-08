package org.sid.cinema.service;

import org.sid.cinema.dao.*;
import org.sid.cinema.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

@Service
@Transactional
public class CinemaInitServiceImpl implements IcinemaInitService {

    @Autowired
    private VilleRepository villeRepository;
    @Autowired
    private CinemaRepository cinemaRepository;
    @Autowired
    private SalleRepository salleRepository;
    @Autowired
    private PlaceRepository placeRepository;
    @Autowired
    private SeanceRepository seanceRepository;
    @Autowired
    private CategorieRepository categorieRepository;
    @Autowired
    private FilmRepository filmRepository;
    @Autowired
    private ProjectionRepository projectionRepository;
    @Autowired
    private TicketRepository ticketRepository;
    @Override
    public void initVilles() {
        /*cette algorithme nous permet de creer un ensemble de ville avec des nom*/
        Stream.of("Casablanca", "Marrakech", "Rabat", "Tanger").forEach(nameville->{
            Ville ville = new Ville();
            ville.setName(nameville);
            villeRepository.save(ville);
        });
    }

    @Override
    public void initCinemas() {
        /* avec cette algorithme pour chaque ville on a une collections de cinema de meme nom dans les ville differente*/
        villeRepository.findAll().forEach(v->{
            Stream.of("MegaRama", "Imax", "Founoun", "Chahrazad").forEach(nameCinema->{
                Cinema cinema = new Cinema();
                cinema.setName(nameCinema);
                cinema.setVille(v);
                cinema.setNombreSalles(3+ (int)(Math.random()*7));
                cinemaRepository.save(cinema);
            });});
    }

    @Override
    public void initSalles() {
        /*avec cette algorithme nous avons pour des cinemas une collection de salles differentes*/
        cinemaRepository.findAll().forEach(cinema->{
            for(int i=0; i< cinema.getNombreSalles(); i++){
                Salle salle = new Salle();
                salle.setName("salle " +(i+1));
                salle.setCinema(cinema);
                salle.setNombrePlace(15+ (int)(Math.random()*20));
                salleRepository.save(salle);
            }
        });

    }

    @Override
    public void initPlaces() {
        salleRepository.findAll().forEach(salle -> {
            for(int i=0; i< salle.getNombrePlace(); i++){
                Place place = new Place();
                place.setNumero(i+1);
                place.setSalle(salle);
                placeRepository.save(place);
            }
        });

    }

    @Override
    public void initSeances() {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
       Stream.of("12:00", "15:45", "18:00", "21:00").forEach(heureseance->{
           Seance seance = new Seance();
           try {
               seance.setHeureDebut(dateFormat.parse(heureseance));
               seanceRepository.save(seance);
           } catch (ParseException e) {
               e.printStackTrace();
           }
       });
    }

    @Override
    public void initCategorie() {
        Stream.of("Histoire","Actions", "Fiction", "Drama").forEach(nameCatecorie->{
            Categorie categorie = new Categorie();
            categorie.setName(nameCatecorie);
            categorieRepository.save(categorie);
        });

    }

    @Override
    public void initFilms() {
        double[] durees = new double[]{1,1.5,2,2.5,3};
        //List<Categorie> categories = categorieRepository.findAll();
        categorieRepository.findAll().forEach(categorie->{
            Stream.of("Game Of Thrones", "Transporteur", "Seigneur Des Anneaux", "La League Des Justicie", "Spider Man", "Iron Man", "Cat Woman").forEach(titreFilm->{
                Film film = new Film();
                film.setTitre(titreFilm);
                film.setRealisateurs("varuspro");
                film.setDuree(durees[new Random().nextInt(durees.length)]);
                film.setPhoto(titreFilm.replace(" ", "")+".jpg");
                film.setCategorie(categorie);
                //film.setCategorie(categories.get(new Random().nextInt(categories.size())));
                filmRepository.save(film);

            });
        });
    }

    @Override
    public void initProjections() {
        double[] prices = new double[]{30,50,70,60,90,100};
        List<Film> films = filmRepository.findAll();
        villeRepository.findAll().forEach(ville -> {
            ville.getCinemas().forEach(cinema -> {
                cinema.getSalles().forEach(salle -> {
                    int index = new Random().nextInt(films.size());
                        Film film = films.get(index);
                        seanceRepository.findAll().forEach(seance -> {
                            Projection projection = new Projection();
                            projection.setDateProjection(new Date());
                            projection.setFilm(film);
                            projection.setPrix(prices[new Random().nextInt(prices.length)]);
                            projection.setSalle(salle);
                            projection.setSeance(seance);
                            projectionRepository.save(projection);
                        });
                });
            });
        });

    }

    @Override
    public void initTickets() {
        projectionRepository.findAll().forEach(projection -> {
            projection.getSalle().getPlaces().forEach(place -> {
                Ticket ticket = new Ticket();
                ticket.setPlace(place);
                ticket.setPrix(projection.getPrix());
                ticket.setProjection(projection);
                ticket.setReservee(false);
                ticketRepository.save(ticket);
            });
        });
    }
}

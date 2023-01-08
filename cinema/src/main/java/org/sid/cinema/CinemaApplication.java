package org.sid.cinema;

import org.sid.cinema.entities.*;
import org.sid.cinema.service.AccountService;
import org.sid.cinema.service.IcinemaInitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class CinemaApplication implements CommandLineRunner {

	/*//HTTP port
	@Value("${http.port}")
	private int httpPort;*/

	@Autowired
	private IcinemaInitService cinemaInitService;

	@Autowired
	private RepositoryRestConfiguration repositoryRestConfiguration;

	@Autowired
	private AccountService accountService;

	public static void main(String[] args) {
		SpringApplication.run(CinemaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		repositoryRestConfiguration.exposeIdsFor(Film.class, Salle.class, Ticket.class, Categorie.class, Ville.class, Seance.class, Place.class, Cinema.class, Cinema.class,Projection.class);
		cinemaInitService.initVilles();
		cinemaInitService.initCinemas();
		cinemaInitService.initSalles();
		cinemaInitService.initPlaces();
		cinemaInitService.initSeances();
		cinemaInitService.initCategorie();
		cinemaInitService.initFilms();
		cinemaInitService.initProjections();
		cinemaInitService.initTickets();

		AppUser user1 = accountService.saveUser(new AppUser(null,"admin","bobo@gmail.com",false,"425687-asdfg_ASD_45",null, "1234", null));
		AppUser user2= accountService.saveUser(new AppUser(null,"user","loic@gmail.com",false,"4587-ASDF_452", null,"1234", null));
		accountService.saveRole(new AppRole(null,"ADMIN"));
		accountService.saveRole(new AppRole(null,"USER"));
		accountService.addRoleToUse("admin","ADMIN");
		accountService.addRoleToUse("admin","USER");
		accountService.addRoleToUse("user","USER");
	}

	@Bean
	public BCryptPasswordEncoder getBPE(){
		return new BCryptPasswordEncoder();
	}


    /*// Let's configure additional connector to enable support for both HTTP and HTTPS
    @Bean
    public ServletWebServerFactory servletContainer() {
        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
        tomcat.addAdditionalTomcatConnectors(createStandardConnector());
        return tomcat;
    }

    private Connector createStandardConnector() {
        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
        connector.setPort(httpPort);
        return connector;
    }*/
}

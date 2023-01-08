package org.sid.cinema.dao;

import org.sid.cinema.entities.Cinema;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@RepositoryRestResource
@CrossOrigin("*")
public interface CinemaRepository extends JpaRepository<Cinema, Long> {

    @RestResource(path = "/cinemapage")
    public Page<Cinema> findByNameContains(@Param("mc") String des, Pageable pageable);
}

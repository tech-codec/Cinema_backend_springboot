package org.sid.cinema.dao;

import org.sid.cinema.entities.Ville;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.web.bind.annotation.CrossOrigin;


@CrossOrigin("*")
@RepositoryRestResource
public interface VilleRepository extends JpaRepository<Ville, Long> {

    @RestResource(path = "/villePage")
    public Page<Ville> findByNameContains(@Param("mc") String des, Pageable pageable);
}

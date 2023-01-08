package org.sid.cinema.entities;

import org.springframework.data.rest.core.config.Projection;

import java.util.Collection;

@Projection(name = "cinema", types ={Cinema.class})
public interface CinemaProjection {
    public Long getId();
    public String getName();
    public double getLongitude();
    public double getLatitude();
    public double getAltitude();
    public int getNombreSalles();
    public Collection<Salle> getSalles();
    public Ville getVille();

}

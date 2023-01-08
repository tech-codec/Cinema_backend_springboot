package org.sid.cinema.entities;

import java.util.Collection;

@org.springframework.data.rest.core.config.Projection(name = "salle",types = Salle.class)
public interface SalleProjection {

    public Long getId();
    public String getName();
    public int getNombrePlace();
    public Cinema getCinema();
    public Collection<Place> getPlaces();
    public Collection<Projection> getProjections();
}

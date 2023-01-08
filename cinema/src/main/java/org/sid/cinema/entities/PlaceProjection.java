package org.sid.cinema.entities;

import org.springframework.data.rest.core.config.Projection;

import java.util.Collection;

@Projection(name = "place",types = Place.class)
public interface PlaceProjection {
    public Long getId();
    public int getNumero();
    public double getLongitude();
    public double getLatitude();
    public double getAltitude();
    public Salle getSalle();
    public Collection<Ticket> getTickets();

}

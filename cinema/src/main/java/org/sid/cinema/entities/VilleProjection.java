package org.sid.cinema.entities;

import org.springframework.data.rest.core.config.Projection;

import java.util.Collection;

@Projection(name = "ville",types={Ville.class})
public interface VilleProjection {
    public Long getId();
    public String getName();
    public double getLongitude();
    public double getLatitude();
    public double getAltitude();
    public Collection<Cinema> getCinemas();
}

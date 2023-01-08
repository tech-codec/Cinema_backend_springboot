package org.sid.cinema.entities;

import java.util.Collection;

@org.springframework.data.rest.core.config.Projection(name = "film", types = {org.sid.cinema.entities.Film.class})
public interface FilmProjection {

    public Long getId();
    public String getTitre();
    public double getDuree();
    public String getRealisateurs();
    public String getDescription();
    public Categorie getCategorie();
    public String getPhoto();
    public int getDateSortie();
    public Collection<Projection> getProjections();

}

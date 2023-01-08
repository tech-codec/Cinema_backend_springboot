package org.sid.cinema.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor
public class Film implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titre;
    private double duree;
    private String realisateurs;
    private String description;
    private String photo;
    private int dateSortie;
    @ManyToOne
    private Categorie categorie;
    @OneToMany(mappedBy = "film")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Collection<Projection> projections;

    public void addProjectionToFilm(Projection projection){
        if (getProjections() == null){
            this.projections = new ArrayList<>();
        }
        getProjections().add(projection);
        projection.setFilm(this);
    }
}

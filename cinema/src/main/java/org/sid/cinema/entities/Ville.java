package org.sid.cinema.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

@Entity @Data @AllArgsConstructor @NoArgsConstructor
public class Ville implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private double longitude;
    private double latitude;
    private double altitude;
    @OneToMany(mappedBy = "ville")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Collection<Cinema> cinemas;

    public void addCinemaToVille(Cinema cinema){
        if(getCinemas() == null){
            this.cinemas = new ArrayList<>();
        }
        getCinemas().add(cinema);
        cinema.setVille(this);
    }
}

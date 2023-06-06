package com.revature.pokemon.entities;

import java.util.Date;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "builds")
public class Build {
    

    @Id
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Date create_time;

    @Column(nullable = false)
    private Date edit_time;

    @Column
    private String description;

    @ManyToOne
    @JoinColumn(name = "team_id")
    @JsonBackReference
    private Team team;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    @ManyToOne
    @JoinColumn(name = "pokemon_id")
    @JsonBackReference
    private Pokemon pokemon;

    public Build(String name, String description, User user, Pokemon pokemon) {
        this.name = name;
        this.description = description;
        this.user = user;
        this.pokemon = pokemon;
        this.create_time = new Date(System.currentTimeMillis());
        this.edit_time = new Date(System.currentTimeMillis());
        this.id = UUID.randomUUID().toString();
    }
}

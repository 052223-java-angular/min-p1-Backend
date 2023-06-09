package com.revature.pokemon.entities;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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

    @Column(nullable = false)
    private String pokemonName;

    @Column
    private String description;

    @ManyToMany(mappedBy = "builds",fetch = FetchType.LAZY) 
    @JsonBackReference
    private Set<Team> teams;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ability_id")
    @JsonBackReference
    private Ability ability;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nature_id")
    @JsonBackReference
    private Nature nature;

    @ManyToMany(fetch = FetchType.LAZY)
    @JsonManagedReference
    @JoinTable(
        name = "learned_moves",
        joinColumns = @JoinColumn(name = "build_id"),
        inverseJoinColumns = @JoinColumn(name = "move_id")
    )
    private Set<Move> moves;

    public Build(String name, String description, User user, Nature nature, Ability ability, Set<Move> moves, String pokemonName) {
        this.name = name;
        this.description = description;
        this.user = user;
        this.nature = nature;
        this.ability = ability;
        this.moves = moves;
        this.pokemonName = pokemonName;
        this.create_time = new Date(System.currentTimeMillis());
        this.edit_time = new Date(System.currentTimeMillis());
        this.id = UUID.randomUUID().toString();
    }
}

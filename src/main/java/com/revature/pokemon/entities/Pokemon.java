package com.revature.pokemon.entities;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "pokemons")
public class Pokemon {

    @Id
    private String id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "ability_id")
    @JsonBackReference
    private Ability ability;

    @ManyToOne
    @JoinColumn(name = "nature_id")
    @JsonBackReference
    private Nature nature;

    @ManyToMany
    @JoinTable(
        name = "learned_moves",
        joinColumns = @JoinColumn(name = "pokemon_id"),
        inverseJoinColumns = @JoinColumn(name = "move_id")
    )
    private Set<Move> moves;

    public Pokemon(String name, Nature nature, Ability ability) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.nature = nature;
        this.ability = ability;
    }
}

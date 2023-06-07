package com.revature.pokemon.entities;

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
import jakarta.persistence.OneToMany;
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

    @OneToMany(mappedBy = "pokemon", fetch = FetchType.LAZY)
    @JsonManagedReference
    private Set<Build> builds; 

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ability_id")
    @JsonBackReference
    private Ability ability;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nature_id")
    @JsonBackReference
    private Nature nature;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "learned_moves",
        joinColumns = @JoinColumn(name = "pokemon_id"),
        inverseJoinColumns = @JoinColumn(name = "move_id")
    )
    private Set<Move> moves;

    public Pokemon(String name, Nature nature, Ability ability, Set<Move> moves) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.nature = nature;
        this.ability = ability;
        this.moves = moves;
    }
}

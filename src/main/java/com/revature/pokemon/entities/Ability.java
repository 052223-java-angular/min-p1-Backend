package com.revature.pokemon.entities;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
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
@Table(name = "abilities")
public class Ability {

    @Id
    private String id;
    
    @Column(unique = true, nullable =  false)
    private String name;

    @OneToMany(mappedBy = "ability", fetch = FetchType.LAZY)
    @JsonManagedReference
    private Set<Build> pokemons;
}

package com.revature.pokemon.entities;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
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
@Table(name = "moves")
public class Move {
    @Id
    private String id;

    @Column(unique = true, nullable =  false)
    private String name;

    @ManyToMany(mappedBy = "moves",fetch = FetchType.LAZY)
    @JsonBackReference
    private Set<Build> builds;
}

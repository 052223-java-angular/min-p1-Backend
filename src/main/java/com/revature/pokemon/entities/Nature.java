package com.revature.pokemon.entities;

import java.util.Set;

import org.hibernate.annotations.SecondaryRow;

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
@SecondaryRow
@Entity
@Table(name = "natures")
public class Nature {
    @Id
    private String id;

    @Column(unique = true, nullable = false)
    private String name;

    @OneToMany(mappedBy = "nature", fetch = FetchType.LAZY)
    @JsonManagedReference
    private Set<Build> Builds;
}

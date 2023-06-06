package com.revature.pokemon.entities;

import java.util.Date;
import java.util.Set;



import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
@Table(name = "posts")
public class Post {
    @Id
    private String id;
    
    @Column(nullable = false)
    private String post_title;

    @Column(nullable = false)
    private Date create_time;

    @Column(nullable = false)
    private Date edit_time;

    @Column(nullable = false)
    private String message;

    @Column(nullable = false)
    private int upvote;
    
    @Column(nullable = false)
    private int downvote;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY)
    @JsonManagedReference
    private Set<Comment> comments;

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY)
    @JsonManagedReference
    private Set<PostVote> postVotes;
}

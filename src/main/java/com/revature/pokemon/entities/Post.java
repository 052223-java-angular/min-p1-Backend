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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY)
    @JsonManagedReference
    private Set<Comment> comments;

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY)
    @JsonManagedReference
    private Set<PostVote> postVotes;

    public Post(String postTitle, String message, User user) {
        this.post_title = postTitle;
        this.message = message;
        this.user = user;
        this.create_time = new Date(System.currentTimeMillis());
        this.edit_time = new Date(System.currentTimeMillis());
        this.id = UUID.randomUUID().toString();
    }
}

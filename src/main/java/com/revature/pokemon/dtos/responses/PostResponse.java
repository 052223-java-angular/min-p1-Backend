package com.revature.pokemon.dtos.responses;

import java.util.Date;
import java.util.Set;

import com.revature.pokemon.entities.Comment;
import com.revature.pokemon.entities.Post;
import com.revature.pokemon.entities.PostVote;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PostResponse {
    private String id;
    private String postTitle;
    private String message;
    private Date create_time;
    private Date edit_time;
    private Set<Comment> comments;
    private Set<PostVote> votes;
    private String username;

    public PostResponse(Post post) {
        this.id = post.getId();
        this.postTitle = post.getPost_title();
        this.message = post.getMessage();
        this.create_time = post.getCreate_time();
        this.edit_time = post.getEdit_time();
        this.comments = post.getComments();
        this.votes = post.getPostVotes();
        this.username = post.getUser().getUsername();
    }

}

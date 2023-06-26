package com.revature.pokemon.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.revature.pokemon.entities.Comment;
import com.revature.pokemon.entities.CommentVote;
import com.revature.pokemon.entities.User;
import com.revature.pokemon.repositories.CommentVoteRepository;

public class CommentVoteServiceTest {
    @Mock
    CommentVoteRepository commentVoteRepo;

    @InjectMocks
    CommentVoteService commentVoteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testDelete() {
        CommentVote vote = new CommentVote();

        commentVoteService.delete(vote);

        verify(commentVoteRepo).delete(vote);
    }

    @Test
    void testFindById() {
        String id = "a real id";
        CommentVote vote = new CommentVote();
        vote.setId(id);

        when(commentVoteRepo.findById(id)).thenReturn(java.util.Optional.of(vote));

        CommentVote result = commentVoteService.findById(id);

        assertEquals(vote, result);

    }

    @Test
    void testFindByUserAndComment() {
        User user = new User();
        Comment comment = new Comment();
        CommentVote commentVote = new CommentVote();

        when(commentVoteRepo.findByUserAndComment(user, comment)).thenReturn(java.util.Optional.of(commentVote));

        CommentVote result = commentVoteService.findByUserAndComment(user, comment);

        assertEquals(commentVote, result);
    }

    @Test
    void testVote() {
        CommentVote vote = new CommentVote();
        vote.setId("a real id");

        commentVoteService.vote(vote);

        verify(commentVoteRepo).save(vote);
    }
}

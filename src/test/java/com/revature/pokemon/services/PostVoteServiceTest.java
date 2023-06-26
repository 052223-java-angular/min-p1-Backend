package com.revature.pokemon.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.revature.pokemon.entities.Post;
import com.revature.pokemon.entities.PostVote;
import com.revature.pokemon.entities.User;
import com.revature.pokemon.repositories.PostVoteRepository;

public class PostVoteServiceTest {
    @Mock
    PostVoteRepository postVoteRepo;

    @InjectMocks
    PostVoteService postVoteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testDelete() {
        PostVote vote = new PostVote();

        postVoteService.delete(vote);

        verify(postVoteRepo).delete(vote);
    }

    @Test
    void testFindById() {
        String id = "a real id";
        PostVote vote = new PostVote();
        vote.setId(id);

        when(postVoteRepo.findById(id)).thenReturn(java.util.Optional.of(vote));

        PostVote result = postVoteService.findById(id);

        assertEquals(vote, result);
    }

    @Test
    void testFindByUserAndPost() {
        User user = new User();
        Post post = new Post();
        PostVote postVote = new PostVote();

        when(postVoteRepo.findByUserAndPost(user, post)).thenReturn(java.util.Optional.of(postVote));

        PostVote result = postVoteService.findByUserAndPost(user, post);

        assertEquals(postVote, result);
    }

    @Test
    void testVote() {
        PostVote vote = new PostVote();
        vote.setId("a real id");

        postVoteService.vote(vote);

        verify(postVoteRepo).save(vote);
    }
}

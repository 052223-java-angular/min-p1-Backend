package com.revature.pokemon.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.revature.pokemon.dtos.requests.PostDeleteRequest;
import com.revature.pokemon.dtos.requests.PostVoteRequest;
import com.revature.pokemon.dtos.responses.PostResponse;
import com.revature.pokemon.dtos.requests.ModifyPostRequest;
import com.revature.pokemon.dtos.requests.NewPostRequest;
import com.revature.pokemon.entities.Post;
import com.revature.pokemon.entities.PostVote;
import com.revature.pokemon.entities.User;
import com.revature.pokemon.repositories.PostRepository;

public class PostServiceTest {
    @Mock
    PostRepository postRepo;

    @Mock
    UserService userService;

    @Mock
    PostVoteService postVoteService;

    @InjectMocks
    PostService postService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreate() {
        NewPostRequest req = new NewPostRequest();
        req.setMessage("hi");
        req.setPostTitle("title");
        req.setUserId("a real user id");

        User user = new User();
        user.setId(req.getUserId());

        Post post = new Post(req.getPostTitle(), req.getMessage(), user);

        when(userService.findById(req.getUserId())).thenReturn(user);

        postService.create(req);

        ArgumentCaptor<Post> postCaptor = ArgumentCaptor.forClass(Post.class);
        verify(postRepo).save(postCaptor.capture());
        Post savedPost = postCaptor.getValue();
        assertEquals(savedPost.getUser(), post.getUser());
    }

    @Test
    void testDelete() {
        PostDeleteRequest req = new PostDeleteRequest();
        req.setPostId("real post id");
        req.setUserId("real user id");

        User user = new User();
        user.setId(req.getUserId());
        Post post = new Post();
        post.setId(req.getPostId());
        post.setUser(user);

        when(postRepo.findById(req.getPostId())).thenReturn(java.util.Optional.of(post));

        postService.delete(req);

        verify(postRepo).delete(post);
    }

    @Test
    void testFindAll() {
        User user = new User();
        Post post = new Post();
        post.setId("something");
        post.setUser(user);
        List<Post> posts = new ArrayList<>();
        posts.add(post);
        List<PostResponse> responsesList = new ArrayList<>();
        PostResponse response = new PostResponse(post);
        responsesList.add(response);

        when(postRepo.findAll()).thenReturn(posts);

        List<PostResponse> results = postService.findAll();

        assertEquals(responsesList.get(0).getId(), results.get(0).getId());
    }

    @Test
    void testFindById() {
        String id = "real id";

        Post post = new Post();
        post.setId(id);

        when(postRepo.findById(id)).thenReturn(java.util.Optional.of(post));

        Post result = postService.findById(id);

        assertEquals(post, result);
    }

    @Test
    void testFindByIdResponse() {
        String id = "real id";

        Post post = new Post();
        post.setId(id);

        when(postRepo.findById(id)).thenReturn(java.util.Optional.of(post));

        Post result = postService.findById(id);

        assertEquals(post, result);
    }

    @Test
    void testFindByUserId() {
        String id = "a real id";
        User user = new User();
        user.setId(id);
        Post post = new Post();
        post.setId("something");
        post.setUser(user);
        List<Post> posts = new ArrayList<>();
        posts.add(post);
        List<PostResponse> responsesList = new ArrayList<>();
        PostResponse response = new PostResponse(post);
        responsesList.add(response);

        when(postRepo.findAllByUserId(id)).thenReturn(posts);

        List<PostResponse> results = postService.findByUserId(id);

        assertEquals(responsesList.get(0).getId(), results.get(0).getId());
    }

    @Test
    void testModify() {
        ModifyPostRequest req = new ModifyPostRequest();
        req.setPostId("real post id");
        req.setUserId("real user id");

        Post post = new Post();
        User user = new User();
        user.setId(req.getUserId());
        post.setId(req.getPostId());
        post.setUser(user);

        when(postRepo.findById(req.getPostId())).thenReturn(java.util.Optional.of(post));

        postService.modify(req);

        verify(postRepo).save(post);
    }

    @Test
    void testVote() {
        PostVoteRequest req = new PostVoteRequest();
        req.setPostId("real post id");
        req.setUserId("real user id");
        req.setVote(false);

        User user = new User();
        user.setId(req.getUserId());
        Post post = new Post();
        post.setId(req.getPostId());
        PostVote vote = new PostVote(user, post, req.isVote());

        when(userService.findById(req.getUserId())).thenReturn(user);
        when(postRepo.findById(req.getPostId())).thenReturn(java.util.Optional.of(post));
        when(postVoteService.findByUserAndPost(user, post)).thenReturn(null);

        postService.vote(req);

        ArgumentCaptor<PostVote> voteCaptor = ArgumentCaptor.forClass(PostVote.class);
        verify(postVoteService).vote(voteCaptor.capture());
        PostVote votedPost = voteCaptor.getValue();
        assertEquals(vote.getPost(), votedPost.getPost());
    }
}

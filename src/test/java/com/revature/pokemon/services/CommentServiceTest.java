package com.revature.pokemon.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.revature.pokemon.dtos.requests.CommentDeleteRequest;
import com.revature.pokemon.dtos.requests.CommentVoteRequest;
import com.revature.pokemon.dtos.requests.ModifyCommentRequest;
import com.revature.pokemon.dtos.requests.NewCommentRequest;
import com.revature.pokemon.entities.Comment;
import com.revature.pokemon.entities.CommentVote;
import com.revature.pokemon.entities.Post;
import com.revature.pokemon.entities.User;
import com.revature.pokemon.repositories.CommentRepository;

public class CommentServiceTest {
    @Mock
    CommentRepository commentRepo;

    @Mock
    UserService userService;

    @Mock
    PostService postService;

    @Mock
    CommentVoteService commentVoteService;

    @InjectMocks
    CommentService commentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreate() {
        NewCommentRequest req = new NewCommentRequest();
        req.setComment("hello there");
        req.setPostId("real post id");
        req.setUserId("real user id");

        User user = new User();
        user.setId(req.getUserId());

        Post post = new Post();
        post.setId(req.getPostId());

        Comment comment = new Comment(req.getComment(), post, user);

        when(userService.findById(req.getUserId())).thenReturn(user);
        when(postService.findById(req.getPostId())).thenReturn(post);

        commentService.create(req);

        ArgumentCaptor<Comment> commentCaptor = ArgumentCaptor.forClass(Comment.class);
        verify(commentRepo).save(commentCaptor.capture());
        Comment savedComment = commentCaptor.getValue();
        assertEquals(comment.getPost(), savedComment.getPost());
    }

    @Test
    void testDelete() {
        CommentDeleteRequest req = new CommentDeleteRequest();
        req.setCommentId("real comment id");
        req.setUserId("real user id");

        User user = new User();
        user.setId(req.getUserId());
        Comment comment = new Comment();
        comment.setId(req.getCommentId());
        comment.setUser(user);

        when(commentRepo.findById(req.getCommentId())).thenReturn(java.util.Optional.of(comment));

        commentService.delete(req);

        verify(commentRepo).delete(comment);

    }

    @Test
    void testFindById() {
        String id = "real id";

        Comment comment = new Comment();
        comment.setId(id);

        when(commentRepo.findById(id)).thenReturn(java.util.Optional.of(comment));

        Comment result = commentService.findById(id);

        assertEquals(comment, result);
    }

    @Test
    void testModify() {
        ModifyCommentRequest req = new ModifyCommentRequest();
        req.setComment("new comment");
        req.setCommentId("real comment id");
        req.setUserId("real user id");

        Comment comment = new Comment();
        User user = new User();
        user.setId(req.getUserId());
        comment.setId(req.getCommentId());
        comment.setComment(req.getComment());
        comment.setUser(user);

        when(commentRepo.findById(req.getCommentId())).thenReturn(java.util.Optional.of(comment));

        commentService.modify(req);

        verify(commentRepo).save(comment);

    }

    @Test
    void testVote() {
        CommentVoteRequest req = new CommentVoteRequest();
        req.setCommentId("real comment id");
        req.setUserId("real user id");
        req.setVote(false);

        User user = new User();
        user.setId(req.getUserId());
        Comment comment = new Comment();
        comment.setId(req.getCommentId());
        CommentVote vote = new CommentVote(user, comment, req.isVote());

        when(userService.findById(req.getUserId())).thenReturn(user);
        when(commentRepo.findById(req.getCommentId())).thenReturn(java.util.Optional.of(comment));
        when(commentVoteService.findByUserAndComment(user, comment)).thenReturn(null);

        commentService.vote(req);

        ArgumentCaptor<CommentVote> voteCaptor = ArgumentCaptor.forClass(CommentVote.class);
        verify(commentVoteService).vote(voteCaptor.capture());
        CommentVote votedComment = voteCaptor.getValue();
        assertEquals(vote.getComment(), votedComment.getComment());
    }
}

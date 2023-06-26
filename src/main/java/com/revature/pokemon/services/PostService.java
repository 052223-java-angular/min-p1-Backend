package com.revature.pokemon.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.revature.pokemon.dtos.requests.ModifyPostRequest;
import com.revature.pokemon.dtos.requests.NewPostRequest;
import com.revature.pokemon.dtos.requests.PostDeleteRequest;
import com.revature.pokemon.dtos.requests.PostVoteRequest;
import com.revature.pokemon.dtos.responses.PostResponse;
import com.revature.pokemon.entities.Post;
import com.revature.pokemon.entities.PostVote;
import com.revature.pokemon.entities.User;
import com.revature.pokemon.repositories.PostRepository;
import com.revature.pokemon.utils.custom_exceptions.PermissionException;
import com.revature.pokemon.utils.custom_exceptions.ResourceNotFoundException;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
/**
 * The PostService class provides methods for managing posts.
 */
public class PostService {
    PostRepository postRepo;
    UserService userService;
    PostVoteService postVoteService;

    /**
     * The logger instance for logging messages related to PostService.
     */
    private static final Logger logger = LoggerFactory.getLogger(PostService.class);

    /**
     * Creates a new post.
     *
     * @param req the NewPostRequest object containing the details of the new post
     */
    public void create(NewPostRequest req) {
        logger.info("Creating new post");

        User user = userService.findById(req.getUserId());
        Post post = new Post(req.getPostTitle(), req.getMessage(), user);
        postRepo.save(post);

        logger.info("Created new post");
    }

    /**
     * Finds a post by its ID.
     *
     * @param postId the ID of the post to be found
     * @return the Post object with the specified ID
     * @throws ResourceNotFoundException if the post with the specified ID is not
     *                                   found
     */
    public Post findById(String postId) {
        logger.info("Finding post by id");
        return postRepo.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post (" + postId + ") not found!"));
    }

    /**
     * Votes on a post.
     *
     * @param req the PostVoteRequest object containing the details of the vote
     */
    public void vote(PostVoteRequest req) {
        logger.info("Voting on post");

        User user = userService.findById(req.getUserId());
        Post post = findById(req.getPostId());
        PostVote vote = postVoteService.findByUserAndPost(user, post);
        if (vote == null) {
            vote = new PostVote(user, post, req.isVote());
        } else {
            if (vote.isVote() == req.isVote()) {
                postVoteService.delete(vote);
                return;
            } else {
                vote.setVote(req.isVote());
            }
        }
        postVoteService.vote(vote);

        logger.info("Voted post");
    }

    /**
     * Modifies a post.
     *
     * @param req the ModifyPostRequest object containing the details of the
     *            modifications
     * @throws PermissionException if the user does not have permission to modify
     *                             the post
     */
    public void modify(ModifyPostRequest req) {
        logger.info("Modifying post");

        Post post = findById(req.getPostId());

        if (!req.getUserId().equals(post.getUser().getId())) {
            throw new PermissionException("You do not have permission to make this change!");
        }

        post.setMessage(req.getMessage());
        post.setPost_title(req.getPostTitle());
        post.setEdit_time(new Date(System.currentTimeMillis()));

        postRepo.save(post);

        logger.info("Modified post");
    }

    /**
     * Deletes a post.
     *
     * @param req the PostDeleteRequest object containing the ID of the post to be
     *            deleted
     * @throws PermissionException if the user does not have permission to delete
     *                             the post
     */
    public void delete(PostDeleteRequest req) {
        logger.info("Deleting post");

        Post post = findById(req.getPostId());

        if (!req.getUserId().equals(post.getUser().getId())) {
            throw new PermissionException("You do not have permission to make this change!");
        }

        postRepo.delete(post);

        logger.info("Deleted post");
    }

    /**
     * Finds all posts.
     *
     * @return a list of PostResponse objects representing all the posts
     */
    public List<PostResponse> findAll() {
        logger.info("Finding all posts");

        List<Post> posts = postRepo.findAll();
        List<PostResponse> responsesList = new ArrayList<>();

        for (Post post : posts) {
            responsesList.add(new PostResponse(post));
        }

        return responsesList;
    }

    /**
     * Finds posts by user ID.
     *
     * @param user_id the ID of the user
     * @return a list of PostResponse objects representing the posts made by the
     *         user with the specified ID
     */
    public List<PostResponse> findByUserId(String user_id) {
        logger.info("Finding posts by user id");

        List<Post> posts = postRepo.findAllByUserId(user_id);
        List<PostResponse> responseList = new ArrayList<>();

        for (Post post : posts) {
            responseList.add(new PostResponse(post));
        }
        return responseList;
    }

    /**
     * Finds a post by its ID and returns it as a PostResponse object.
     *
     * @param id the ID of the post
     * @return the PostResponse object representing the post with the specified ID
     * @throws ResourceNotFoundException if the post with the specified ID is not
     *                                   found
     */
    public PostResponse findByIdResponse(String id) {
        logger.info("Finding post by id");

        Post post = postRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post (" + id + ") not found!"));
        return new PostResponse(post);
    }
}

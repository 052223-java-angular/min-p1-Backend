package com.revature.pokemon.services;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.revature.pokemon.dtos.requests.CommentDeleteRequest;
import com.revature.pokemon.dtos.requests.CommentVoteRequest;
import com.revature.pokemon.dtos.requests.ModifyCommentRequest;
import com.revature.pokemon.dtos.requests.NewCommentRequest;
import com.revature.pokemon.entities.Comment;
import com.revature.pokemon.entities.CommentVote;
import com.revature.pokemon.entities.Post;
import com.revature.pokemon.entities.User;
import com.revature.pokemon.repositories.CommentRepository;
import com.revature.pokemon.utils.custom_exceptions.PermissionException;
import com.revature.pokemon.utils.custom_exceptions.ResourceNotFoundException;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
/**
 * The CommentService class provides methods for managing comments.
 */
public class CommentService {
    CommentRepository commentRepo;
    UserService userService;
    PostService postService;
    CommentVoteService commentVoteService;

    /**
     * The logger instance for logging messages related to CommentService.
     */
    private static final Logger logger = LoggerFactory.getLogger(CommentService.class);

    /**
     * Creates a new comment based on the provided request.
     *
     * @param req the NewCommentRequest containing comment information
     * @throws ResourceNotFoundException if the user or post associated with the
     *                                   comment is not found
     */
    public void create(NewCommentRequest req) throws ResourceNotFoundException {
        logger.info("Creating new comment");

        User user = userService.findById(req.getUserId());
        Post post = postService.findById(req.getPostId());
        Comment comment = new Comment(req.getComment(), post, user);
        commentRepo.save(comment);

        logger.info("Created new comment");
    }

    /**
     * Finds a comment by its ID.
     *
     * @param commentId the ID of the comment to be found
     * @return the Comment object with the specified ID
     * @throws ResourceNotFoundException if the comment is not found
     */
    public Comment findById(String commentId) throws ResourceNotFoundException {
        logger.info("Finding comment by id");

        return commentRepo.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment (" + commentId + ") not found!"));
    }

    /**
     * Votes on a comment based on the provided request.
     *
     * @param req the CommentVoteRequest containing vote information
     * @throws ResourceNotFoundException if the user or comment associated with the
     *                                   vote is not found
     */
    public void vote(CommentVoteRequest req) throws ResourceNotFoundException {
        logger.info("Voting on comment");

        User user = userService.findById(req.getUserId());
        Comment comment = findById(req.getCommentId());
        CommentVote vote = commentVoteService.findByUserAndComment(user, comment);
        if (vote == null) {
            vote = new CommentVote(user, comment, req.isVote());
        } else {
            if (vote.isVote() == req.isVote()) {
                commentVoteService.delete(vote);
                return;
            } else {
                vote.setVote(req.isVote());
            }
        }
        commentVoteService.vote(vote);

        logger.info("Voted on comment");
    }

    /**
     * Modifies a comment based on the provided request.
     *
     * @param req the ModifyCommentRequest containing comment modification
     *            information
     * @throws ResourceNotFoundException if the comment is not found
     * @throws PermissionException       if the user does not have permission to
     *                                   modify the comment
     */
    public void modify(ModifyCommentRequest req) throws ResourceNotFoundException, PermissionException {
        logger.info("Modifying comment");

        Comment comment = findById(req.getCommentId());

        if (!req.getUserId().equals(comment.getUser().getId())) {
            throw new PermissionException("You do not have permission to make this change!");
        }

        comment.setComment(req.getComment());
        comment.setEdit_time(new Date(System.currentTimeMillis()));

        commentRepo.save(comment);

        logger.info("Modified comment");
    }

    /**
     * Deletes a comment based on the provided request.
     *
     * @param req the CommentDeleteRequest containing comment deletion information
     * @throws ResourceNotFoundException if the comment is not found
     * @throws PermissionException       if the user does not have permission to
     *                                   delete the comment
     */
    public void delete(CommentDeleteRequest req) throws ResourceNotFoundException, PermissionException {
        logger.info("Deleting comment");

        Comment comment = findById(req.getCommentId());

        if (!req.getUserId().equals(comment.getUser().getId())) {
            throw new PermissionException("You do not have permission to make this change!");
        }

        commentRepo.delete(comment);

        logger.info("Deleted comment");
    }
}

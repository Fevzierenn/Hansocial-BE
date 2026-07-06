package org.example.hansocial.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.example.hansocial.entities.Comment;
import org.example.hansocial.entities.Post;
import org.example.hansocial.entities.User;
import org.example.hansocial.exceptions.CommentNotFoundException;
import org.example.hansocial.repos.CommentRepository;
import org.example.hansocial.requests.CommentCreateRequest;
import org.example.hansocial.requests.CommentUpdateRequest;
import org.example.hansocial.responses.CommentResponse;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserService userService;
    private final PostService postService;

    public CommentService(
        CommentRepository commentRepository,
        UserService userService,
        PostService postService
    ) {
        this.commentRepository = commentRepository;
        this.userService = userService;
        this.postService = postService;
    }

    public List<CommentResponse> getAllCommentsWithParam(
        Optional<Long> userId,
        Optional<Long> postId
    ) {
        List<Comment> comments;
        if (userId.isPresent() && postId.isPresent()) {
            comments = commentRepository.findByUserIdAndPostId(
                userId.get(),
                postId.get()
            );
        } else if (userId.isPresent()) {
            comments = commentRepository.findByUserId(userId.get());
        } else if (postId.isPresent()) {
            comments = commentRepository.findByPostId(postId.get());
        } else comments = commentRepository.findAll();
        return comments
            .stream()
            .map(CommentResponse::new)
            .collect(Collectors.toList());
    }

    // Entity-returning — for internal use by other services
    public Comment getSingleCommentById(Long commentId) {
        return commentRepository
            .findById(commentId)
            .orElseThrow(() ->
                new CommentNotFoundException(
                    "Comment with id " + commentId + " not found."
                )
            );
    }

    // DTO-returning — for the controller
    public CommentResponse getSingleCommentResponseById(Long commentId) {
        return new CommentResponse(getSingleCommentById(commentId));
    }

    public CommentResponse createComment(CommentCreateRequest request) {
        User user = userService.getOneUserById(request.getUserId());
        Post post = postService.getOnePostById(request.getPostId());
        Comment commentToSave = Comment.builder()
            .post(post)
            .user(user)
            .text(request.getText())
            .createDate(new Date())
            .build();
        return new CommentResponse(commentRepository.save(commentToSave));
    }

    public CommentResponse updateSingleCommentById(
        Long commentId,
        CommentUpdateRequest request
    ) {
        Comment comment = getSingleCommentById(commentId);
        comment.setText(request.getText());
        return new CommentResponse(commentRepository.save(comment));
    }

    public void deleteOneCommentById(Long commentId) {
        getSingleCommentById(commentId); // throws CommentNotFoundException if not found
        commentRepository.deleteById(commentId);
    }
}

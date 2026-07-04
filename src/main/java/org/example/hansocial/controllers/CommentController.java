package org.example.hansocial.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.Optional;
import org.example.hansocial.entities.Comment;
import org.example.hansocial.requests.CommentCreateRequest;
import org.example.hansocial.requests.CommentUpdateRequest;
import org.example.hansocial.responses.CommentResponse;
import org.example.hansocial.services.CommentService;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/comments")
@Tag(name = "Comments")
public class CommentController {

    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping
    @Operation(summary = "Get all comments")
    public List<CommentResponse> getAllComments(
        @RequestParam Optional<Long> userId,
        @RequestParam Optional<Long> postId
    ) {
        return commentService.getAllCommentsWithParam(userId, postId);
    }

    @PostMapping
    @Operation(summary = "Create a comment")
    public Comment createOneComment(@RequestBody CommentCreateRequest request) {
        return commentService.createOneComment(request);
    }

    @GetMapping("/{commentId}")
    @Operation(summary = "Get comment by ID")
    public Comment getOneComment(@PathVariable Long commentId) {
        return commentService.getOneCommentById(commentId);
    }

    @PutMapping("/{commentId}")
    @Operation(summary = "Update a comment")
    public Comment updateOneComment(
        @PathVariable Long commentId,
        @RequestBody CommentUpdateRequest request
    ) {
        return commentService.updateOneCommentById(commentId, request);
    }

    @DeleteMapping("/{commentId}")
    @Operation(summary = "Delete a comment")
    public void deleteOneComment(@PathVariable Long commentId) {
        commentService.deleteOneCommentById(commentId);
    }
}

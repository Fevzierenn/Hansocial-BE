package org.example.hansocial.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.Optional;
import org.example.hansocial.requests.CommentCreateRequest;
import org.example.hansocial.requests.CommentUpdateRequest;
import org.example.hansocial.responses.CommentResponse;
import org.example.hansocial.services.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/comments")
@Tag(name = "Comments")
public class CommentController {

    private final CommentService commentService;

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
    public ResponseEntity<CommentResponse> createComment(
        @RequestBody CommentCreateRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
            commentService.createComment(request)
        );
    }

    @GetMapping("/{commentId}")
    @Operation(summary = "Get comment by ID")
    public ResponseEntity<CommentResponse> getSingleCommentById(
        @PathVariable Long commentId
    ) {
        return ResponseEntity.ok(
            commentService.getSingleCommentResponseById(commentId)
        );
    }

    @PutMapping("/{commentId}")
    @Operation(summary = "Update a comment")
    public ResponseEntity<CommentResponse> updateOneComment(
        @PathVariable Long commentId,
        @RequestBody CommentUpdateRequest request
    ) {
        return ResponseEntity.ok(
            commentService.updateSingleCommentById(commentId, request)
        );
    }

    @DeleteMapping("/{commentId}")
    @Operation(summary = "Delete a comment")
    public ResponseEntity<Void> deleteOneComment(@PathVariable Long commentId) {
        commentService.deleteOneCommentById(commentId);
        return ResponseEntity.noContent().build();
    }
}

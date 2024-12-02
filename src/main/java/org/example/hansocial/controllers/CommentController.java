package org.example.hansocial.controllers;

import org.example.hansocial.entities.Comment;
import org.example.hansocial.requests.CommentCreateRequest;
import org.example.hansocial.requests.CommentUpdateRequest;
import org.example.hansocial.responses.CommentResponse;
import org.example.hansocial.services.CommentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/comments")
public class CommentController {

	private CommentService commentService;

	public CommentController(CommentService commentService) {
		this.commentService = commentService;
	}
	
	@GetMapping
	public List<CommentResponse> getAllComments(@RequestParam Optional<Long> userId, 
			@RequestParam Optional<Long> postId) {
		return commentService.getAllCommentsWithParam(userId, postId);
	}

	
	@PostMapping
	public Comment createOneComment(@RequestBody CommentCreateRequest request) {
		return commentService.createOneComment(request);
	}
	
	@GetMapping("/{commentId}")
	public Comment getOneComment(@PathVariable Long commentId) {
		return commentService.getOneCommentById(commentId);
	}
	
	@PutMapping("/{commentId}")
	public Comment updateOneComment(@PathVariable Long commentId, @RequestBody CommentUpdateRequest request) {
		return commentService.updateOneCommentById(commentId, request);
	}
	
	@DeleteMapping("/{commentId}")
	public void deleteOneComment(@PathVariable Long commentId) {
		commentService.deleteOneCommentById(commentId);
	}
}

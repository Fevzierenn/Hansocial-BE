package org.example.hansocial.controllers;

import org.example.hansocial.entities.Post;
import org.example.hansocial.requests.PostCreateRequest;
import org.example.hansocial.requests.PostUpdateRequest;
import org.example.hansocial.responses.PostResponse;
import org.example.hansocial.services.PostService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/posts")
public class PostController {

	private PostService postService;

	public PostController(PostService postService) {
		this.postService = postService;
	}
	
	@GetMapping
	public List<PostResponse> getAllPosts(@RequestParam Optional<Long> userId) {
		return postService.getAllPosts(userId);
	}

	@PostMapping
	public Post createOnePost(@RequestBody PostCreateRequest newPostRequest) {
		return postService.createOnePost(newPostRequest);
	}
	
	
	@GetMapping("/{postId}")
	public PostResponse getOnePost(@PathVariable Long postId) {
		return postService.getOnePostByIdWithLikes(postId);
	}
	
	@PutMapping("/{postId}")
	public Post updateOnePost(@PathVariable Long postId, @RequestBody PostUpdateRequest updatePost) {
		return postService.updateOnePostById(postId, updatePost);
	}
	
	@DeleteMapping("/{postId}")
	public void deleteOnePost(@PathVariable Long postId) {
		postService.deleteOnePostById(postId);
	}
}

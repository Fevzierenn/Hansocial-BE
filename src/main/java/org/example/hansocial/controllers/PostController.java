package org.example.hansocial.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.Optional;
import org.example.hansocial.entities.Post;
import org.example.hansocial.requests.PostCreateRequest;
import org.example.hansocial.requests.PostUpdateRequest;
import org.example.hansocial.responses.PostResponse;
import org.example.hansocial.services.PostService;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/posts")
@Tag(name = "Posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    @Operation(summary = "Get all posts")
    public List<PostResponse> getAllPosts(@RequestParam Optional<Long> userId) {
        return postService.getAllPosts(userId);
    }

    @PostMapping
    @Operation(summary = "Create a post")
    public Post createOnePost(@RequestBody PostCreateRequest newPostRequest) {
        return postService.createOnePost(newPostRequest);
    }

    @GetMapping("/{postId}")
    @Operation(summary = "Get post by ID")
    public PostResponse getOnePost(@PathVariable Long postId) {
        return postService.getOnePostByIdWithLikes(postId);
    }

    @PutMapping("/{postId}")
    @Operation(summary = "Update a post")
    public Post updateOnePost(
        @PathVariable Long postId,
        @RequestBody PostUpdateRequest updatePost
    ) {
        return postService.updateOnePostById(postId, updatePost);
    }

    @DeleteMapping("/{postId}")
    @Operation(summary = "Delete a post")
    public void deleteOnePost(@PathVariable Long postId) {
        postService.deleteOnePostById(postId);
    }
}

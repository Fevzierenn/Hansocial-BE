package org.example.hansocial.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.Optional;
import org.example.hansocial.entities.Like;
import org.example.hansocial.requests.LikeCreateRequest;
import org.example.hansocial.responses.LikeResponse;
import org.example.hansocial.services.LikeService;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/likes")
@Tag(name = "Likes")
public class LikeController {

    private final LikeService likeService;

    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @GetMapping
    @Operation(summary = "Get all likes")
    public List<LikeResponse> getAllLikes(
        @RequestParam Optional<Long> userId,
        @RequestParam Optional<Long> postId
    ) {
        return likeService.getAllLikesWithParam(userId, postId);
    }

    @PostMapping
    @Operation(summary = "Create a like")
    public Like createOneLike(@RequestBody LikeCreateRequest request) {
        return likeService.createOneLike(request);
    }

    @GetMapping("/{likeId}")
    @Operation(summary = "Get like by ID")
    public Like getOneLike(@PathVariable Long likeId) {
        return likeService.getOneLikeById(likeId);
    }

    @DeleteMapping("/{likeId}")
    @Operation(summary = "Delete a like")
    public void deleteOneLike(@PathVariable Long likeId) {
        likeService.deleteOneLikeById(likeId);
    }
}

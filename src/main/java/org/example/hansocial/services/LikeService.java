package org.example.hansocial.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.example.hansocial.entities.Like;
import org.example.hansocial.entities.Post;
import org.example.hansocial.entities.User;
import org.example.hansocial.exceptions.LikeNotFoundException;
import org.example.hansocial.repos.LikeRepository;
import org.example.hansocial.requests.LikeCreateRequest;
import org.example.hansocial.responses.LikeResponse;
import org.springframework.stereotype.Service;

@Service
public class LikeService {

    private final LikeRepository likeRepository;
    private final UserService userService;
    private final PostService postService;

    public LikeService(
        LikeRepository likeRepository,
        UserService userService,
        PostService postService
    ) {
        this.likeRepository = likeRepository;
        this.userService = userService;
        this.postService = postService;
    }

    public List<LikeResponse> getAllLikesWithParam(
        Optional<Long> userId,
        Optional<Long> postId
    ) {
        Long uId = userId.orElse(null);
        Long pId = postId.orElse(null);
        List<Like> list = likeRepository.findByUserIdAndPostIdOptional(
            uId,
            pId
        );
        return list
            .stream()
            .map(LikeResponse::new)
            .collect(Collectors.toList());
    }

    // Entity-returning for internal use of other services
    public Like getLikeById(Long likeId) {
        return likeRepository
            .findById(likeId)
            .orElseThrow(() ->
                new LikeNotFoundException(
                    "Like with id " + likeId + " not found."
                )
            );
    }

    // DTO-returning — for the controller
    public LikeResponse getLikeResponseById(Long likeId) {
        return new LikeResponse(getLikeById(likeId));
    }

    public LikeResponse createOneLike(LikeCreateRequest request) {
        User user = userService.getUserById(request.getUserId());
        Post post = postService.getOnePostById(request.getPostId());
        Like likeToSave = new Like();
        likeToSave.setPost(post);
        likeToSave.setUser(user);
        return new LikeResponse(likeRepository.save(likeToSave));
    }

    public void deleteOneLikeById(Long likeId) {
        getLikeById(likeId); // throws LikeNotFoundException if not found
        likeRepository.deleteById(likeId);
    }
}

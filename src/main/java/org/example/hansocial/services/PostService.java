package org.example.hansocial.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.example.hansocial.entities.Like;
import org.example.hansocial.entities.Post;
import org.example.hansocial.entities.User;
import org.example.hansocial.exceptions.PostNotFoundException;
import org.example.hansocial.repos.LikeRepository;
import org.example.hansocial.repos.PostRepository;
import org.example.hansocial.requests.PostCreateRequest;
import org.example.hansocial.requests.PostUpdateRequest;
import org.example.hansocial.responses.LikeResponse;
import org.example.hansocial.responses.PostResponse;
import org.springframework.stereotype.Service;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final LikeRepository likeRepository;
    private final UserService userService;

    public PostService(
        PostRepository postRepository,
        UserService userService,
        LikeRepository likeRepository
    ) {
        this.postRepository = postRepository;
        this.userService = userService;
        this.likeRepository = likeRepository;
    }

    public List<PostResponse> getAllPosts(Optional<Long> userId) {
        List<Post> postList = userId.isPresent()
            ? postRepository.findByUserId(userId.get())
            : postRepository.findAll();
        return postList
            .stream()
            .map(p -> {
                List<Like> likes = likeRepository.findByUserIdAndPostIdOptional(
                    null,
                    p.getId()
                );
                List<LikeResponse> likeResponses = likes
                    .stream()
                    .map(LikeResponse::new)
                    .toList();
                return new PostResponse(p, likeResponses);
            })
            .toList();
    }

    public Post getOnePostById(Long postId) {
        return postRepository
            .findById(postId)
            .orElseThrow(() ->
                new PostNotFoundException(
                    "Post with id " + postId + " not found."
                )
            );
    }

    public PostResponse getOnePostByIdWithLikes(Long postId) {
        Post post = postRepository
            .findById(postId)
            .orElseThrow(() -> new PostNotFoundException("Post not found"));
        List<LikeResponse> likes = likeRepository
            .findByUserIdAndPostIdOptional(null, postId)
            .stream()
            .map(LikeResponse::new)
            .toList();
        return new PostResponse(post, likes);
    }

	public Post createOnePost(PostCreateRequest newPostRequest) {
		User user = userService.getUserById(newPostRequest.getUserId());
		Post toSave = Post.builder()
				.text(newPostRequest.getText())
				.title(newPostRequest.getTitle())
				.user(user)
				.createDate(new Date()).build();
		return postRepository.save(toSave);
	}

    public Post updateOnePostById(Long postId, PostUpdateRequest updatePost) {
        Post post = postRepository.findById(postId).orElseThrow(()->  new PostNotFoundException("Post not exists for an update"));
        post.setText(updatePost.getText());
            post.setTitle(updatePost.getTitle());
            postRepository.save(post);
            return post;
    }

    public void deleteOnePostById(Long postId) {
        postRepository.deleteById(postId);
    }
}

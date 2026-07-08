package org.example.hansocial.services;

import  org.example.hansocial.entities.User;
import org.example.hansocial.exceptions.UserAlreadyExistsException;
import org.example.hansocial.exceptions.UserNotFoundException;
import  org.example.hansocial.repos.CommentRepository;
import  org.example.hansocial.repos.LikeRepository;
import  org.example.hansocial.repos.PostRepository;
import org.example.hansocial.repos.UserRepository;
import org.example.hansocial.responses.UserResponse;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

	private final UserRepository userRepository;
	private final LikeRepository likeRepository;
	private final CommentRepository commentRepository;
	private final PostRepository postRepository;

	public UserService(UserRepository userRepository, LikeRepository likeRepository, 
			CommentRepository commentRepository, PostRepository postRepository) {
		this.userRepository = userRepository;
		this.likeRepository = likeRepository;
		this.commentRepository = commentRepository;
		this.postRepository = postRepository;
	}

	public List<UserResponse> getAllUsers() {
		return userRepository.findAll().stream().map(UserResponse::new).toList();
	}
	public List<User> getUsers() {
		return userRepository.findAll();
	}

	public UserResponse saveOneUser(User newUser) {
		Boolean mailExists= userRepository.existsByEmailIgnoreCase(newUser.getEmail());
		Boolean usernameExists= userRepository.existsByUserNameIgnoreCase(newUser.getUserName());
		if(mailExists || usernameExists)
			throw new UserAlreadyExistsException("User already exists with this email or username." );
		return new UserResponse(userRepository.save(newUser));
	}

	public UserResponse getOneUserById(Long userId) {
			User user= userRepository.findById(userId).orElseThrow(()-> new UserNotFoundException("User with id " + userId + " not found."));
			return new UserResponse(user);
	}
	public User getUserById(Long userId) {
		return userRepository.findById(userId).orElseThrow(()-> new UserNotFoundException("User with id " + userId + " not found."));
	}

	public UserResponse updateUserProfile(Long userId, Optional<String> userName, Optional<Integer> avatar) {
		User foundedUser = userRepository.findById(userId).orElseThrow(()-> new UserNotFoundException("User not found with this id: "+ userId));
        userName.ifPresent(foundedUser::setUserName);
		avatar.ifPresent(foundedUser::setAvatar);
			return new UserResponse(userRepository.save(foundedUser));
		}

	public void deleteById(Long userId) {
		try {
		userRepository.deleteById(userId);
		}catch(EmptyResultDataAccessException e) { //user zaten yok, db'den empty result gelmiş
			System.out.println("User "+userId+" doesn't exist"); //istersek loglayabiliriz
		}
	}
	
	public List<Object> getUserActivity(Long userId) {
		List<Long> postIds = postRepository.findTopByUserId(userId);
		if(postIds.isEmpty())
			return Collections.emptyList();
		List<Object> comments = commentRepository.findUserCommentsByPostId(postIds);
		List<Object> likes = likeRepository.findUserLikesByPostId(postIds);
		List<Object> result = new ArrayList<>();
		result.addAll(comments);
		result.addAll(likes);
		return result;
	}


    public UserResponse updateUserAvatar(Long userId, int avatar) {
		User theUser = userRepository.findById(userId).orElseThrow(
				() -> new UserNotFoundException("User not found")
		);
			//change avatar user
			theUser.setAvatar(avatar);
			return new UserResponse(userRepository.save(theUser));
    }
}

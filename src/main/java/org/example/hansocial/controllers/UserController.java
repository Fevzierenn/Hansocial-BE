package org.example.hansocial.controllers;

import  org.example.hansocial.entities.User;
import  org.example.hansocial.exceptions.UserNotFoundException;
import org.example.hansocial.responses.UserResponse;
import org.example.hansocial.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.example.hansocial.exceptions.ErrorResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/users")
public class UserController {
	
	private UserService userService;
	
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@GetMapping
	public List<UserResponse> getAllUsers(){
		return userService.getAllUsers().stream().map(u -> new UserResponse(u)).toList();
	}
	
//	@PostMapping
//	public ResponseEntity<?> createUser(@RequestBody User newUser) {
//		User user = userService.saveOneUser(newUser);
//		if(user != null)
//			return new ResponseEntity<>(HttpStatus.OK);
//		return  ResponseEntity.status(HttpStatus.CONFLICT).body("user already exists");
//	}
@PostMapping
public ResponseEntity<?> createUser(@RequestBody User newUser) {
	try {
		User user = userService.saveOneUser(newUser);
		if (user != null) {
			return ResponseEntity.ok(user); // Başarılı durumda kullanıcı nesnesini döndür
		}
		// Conflict durumunda JSON parse edilebilir bir nesne döndür
		return ResponseEntity
				.status(HttpStatus.CONFLICT)
				.body(new ErrorResponse("Kullanıcı zaten mevcut"));
	} catch (Exception e) {
		// Diğer olası hatalar için genel hata yanıtı
		return ResponseEntity
				.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(new ErrorResponse("Kullanıcı oluşturulurken bir hata meydana geldi"));
	}
}
	
	@GetMapping("/{userId}")
	public UserResponse getOneUser(@PathVariable Long userId) {
		User user = userService.getOneUserById(userId);
		if(user == null) {
			throw new UserNotFoundException();
		}
		return new UserResponse(user);
	}
	
	@PutMapping("/{userId}")
	public ResponseEntity<Void> updateOneUser(@PathVariable Long userId, @RequestBody User newUser) {
		User user = userService.updateOneUser(userId, newUser);
		if(user != null) 
			return new ResponseEntity<>(HttpStatus.OK);
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

	}
	
	@DeleteMapping("/{userId}")
	public void deleteOneUser(@PathVariable Long userId) {
		userService.deleteById(userId);
	}
	
	@GetMapping("/activity/{userId}")
	public List<Object> getUserActivity(@PathVariable Long userId) {
		return userService.getUserActivity(userId);
	}
	
	@ExceptionHandler(UserNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	private void handleUserNotFound() {
		
	}
}

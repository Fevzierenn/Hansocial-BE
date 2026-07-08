package org.example.hansocial.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.Optional;
import org.example.hansocial.entities.User;
import org.example.hansocial.responses.UserResponse;
import org.example.hansocial.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/users")
@Tag(name = "Users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @Operation(summary = "Get all users")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PostMapping
    @Operation(summary = "Create a new user")
    public ResponseEntity<UserResponse> createUser(@RequestBody User newUser) {
            UserResponse savedUserResponse = userService.saveOneUser(newUser);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedUserResponse);
    }

    @GetMapping("/{userId}")
    @Operation(summary = "Get user by ID")
    public ResponseEntity<UserResponse> getOneUser(@PathVariable Long userId) {
        UserResponse userResponse = userService.getOneUserById(userId);
        return ResponseEntity.ok(userResponse);
    }

    @PatchMapping("/{userId}")
    @Operation(summary = "Update user")
    public ResponseEntity<UserResponse> updateUserProfile(
        @PathVariable Long userId,
        @RequestParam Optional<String> userName,
        @RequestParam Optional<Integer> avatar
    ) {
        UserResponse updatedUser = userService.updateUserProfile(userId, userName, avatar);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{userId}")
    @Operation(summary = "Delete user")
    public void deleteOneUser(@PathVariable Long userId) {
        userService.deleteById(userId);
    }

    @GetMapping("/activity/{userId}")
    @Operation(summary = "Get user activity")
    public List<Object> getUserActivity(@PathVariable Long userId) {
        return userService.getUserActivity(userId);
    }

    @PatchMapping("/{userId}/avatar")
    @Operation(summary = "Change user avatar")
    public ResponseEntity<?> changeUserAvatar(
        @PathVariable Long userId,
        @RequestParam(name = "avatar") int avatarIndex
    ) {
        UserResponse updatedUser = userService.updateUserAvatar(userId, avatarIndex);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }
}

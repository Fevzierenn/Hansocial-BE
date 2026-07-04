package org.example.hansocial.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.Optional;
import org.example.hansocial.entities.User;
import org.example.hansocial.exceptions.ErrorResponse;
import org.example.hansocial.exceptions.UserNotFoundException;
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

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @Operation(summary = "Get all users")
    public List<UserResponse> getAllUsers() {
        return userService
            .getAllUsers()
            .stream()
            .map(u -> new UserResponse(u))
            .toList();
    }

    @PostMapping
    @Operation(summary = "Create a new user")
    public ResponseEntity<?> createUser(@RequestBody User newUser) {
        try {
            User user = userService.saveOneUser(newUser);
            if (user != null) return ResponseEntity.ok(user);
            return ResponseEntity.status(HttpStatus.CONFLICT).body(
                new ErrorResponse("Kullanıcı zaten mevcut")
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ErrorResponse(
                    "Kullanıcı oluşturulurken bir hata meydana geldi"
                )
            );
        }
    }

    @GetMapping("/{userId}")
    @Operation(summary = "Get user by ID")
    public UserResponse getOneUser(@PathVariable Long userId) {
        User user = userService.getOneUserById(userId);
        if (user == null) throw new UserNotFoundException();
        return new UserResponse(user);
    }

    @PutMapping("/{userId}")
    @Operation(summary = "Update user")
    public ResponseEntity<Void> updateOneUser(
        @PathVariable Long userId,
        @RequestBody User newUser
    ) {
        User user = userService.updateOneUser(userId, newUser);
        if (user != null) return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
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

    @PutMapping("/{userId}/avatar")
    @Operation(summary = "Change user avatar")
    public ResponseEntity<?> changeUserAvatar(
        @PathVariable Long userId,
        @RequestParam(name = "avatar") int avatarIndex
    ) {
        Optional<User> updatedUser = userService.updateUserAvatar(
            userId,
            avatarIndex
        );
        if (updatedUser.isPresent()) return new ResponseEntity<>(
            updatedUser.get(),
            HttpStatus.OK
        );
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    private void handleUserNotFound() {}
}

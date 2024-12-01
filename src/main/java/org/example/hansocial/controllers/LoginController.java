package org.example.hansocial.controllers;


import org.example.hansocial.entities.User;
import org.example.hansocial.exceptions.ErrorResponse;
import org.example.hansocial.exceptions.UserNotFoundException;
import org.example.hansocial.requests.LoginRequest;
import org.example.hansocial.responses.LoginResponse;
import org.example.hansocial.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController()
@RequestMapping("/login")
@CrossOrigin
public class LoginController {

    private UserService userService;
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    private ResponseEntity<?> loginPage(@RequestBody LoginRequest loginRequestObject)
    {
        List<User> users=userService.getAllUsers();
        List<User> user =users.stream().filter(obj->
                obj.getEmail().equals(loginRequestObject.getEmail()) &&
                obj.getPassword().equals(loginRequestObject.getPassword())).toList();

        List<LoginResponse> loginUser = user.stream().map(m ->
                new LoginResponse(m.getId(), m.getUserName(),m.getEmail(),m.getAvatar())).toList();
        if(!loginUser.isEmpty())
        {
            return ResponseEntity.ok(loginUser.get(0));
        }
        return  ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(new UserNotFoundException("User not found"));
    }
}

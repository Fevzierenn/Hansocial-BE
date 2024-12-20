package org.example.hansocial.responses;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data@AllArgsConstructor
public class LoginResponse {
    private Long id;
    private String username;
    private String email;
    private int avatar;
}

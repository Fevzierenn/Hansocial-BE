package org.example.hansocial.requests;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.management.ConstructorParameters;

@Data
@AllArgsConstructor
public class LoginRequest {


    private String email;
    private String password;
}

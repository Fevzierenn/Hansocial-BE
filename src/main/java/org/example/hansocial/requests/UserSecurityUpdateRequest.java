package org.example.hansocial.requests;

import lombok.Data;

@Data
public class UserSecurityUpdateRequest {
    String currentPassword;
    String newPassword;
    String email;
}

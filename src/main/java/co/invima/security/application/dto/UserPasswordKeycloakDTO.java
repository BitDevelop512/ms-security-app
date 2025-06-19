package co.invima.security.application.dto;

import lombok.Data;

@Data
public class UserPasswordKeycloakDTO {
    private String id;
    private String password;
    private String email;
}

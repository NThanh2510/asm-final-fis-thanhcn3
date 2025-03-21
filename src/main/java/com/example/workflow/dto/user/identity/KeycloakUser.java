package com.example.workflow.dto.user.identity;

import lombok.Data;

@Data
public class KeycloakUser {
    private String id; // Keycloak ID
    private String username;
    private String email;
    private String firstName;
    private String lastName;
}

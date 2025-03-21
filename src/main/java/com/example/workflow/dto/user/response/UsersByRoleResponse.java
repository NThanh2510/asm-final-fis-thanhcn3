package com.example.workflow.dto.user.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsersByRoleResponse {
    String id;
    String username;
    String firstName;
    String lastName;
    String email;
}

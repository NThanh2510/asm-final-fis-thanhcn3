package com.example.workflow.dto.user.response;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class UserSessionResponse {

    String userName;
    String lastName;
    String firstName;
    String email;
    String kcid;
    boolean isOnline;
}

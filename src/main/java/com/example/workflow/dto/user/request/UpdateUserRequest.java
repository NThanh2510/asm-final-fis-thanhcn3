package com.example.workflow.dto.user.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateUserRequest {

     String firstName;
     String lastName;
     String email;
//    private boolean enabled;
}


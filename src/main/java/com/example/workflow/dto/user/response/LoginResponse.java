package com.example.workflow.dto.user.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoginResponse {
    private String accessToken;
    private String expiresIn;
    private String refreshToken;
}

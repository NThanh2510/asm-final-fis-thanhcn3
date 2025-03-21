package com.example.workflow.restController;


import com.example.workflow.dto.ApiResponse;
import com.example.workflow.dto.user.request.*;
import com.example.workflow.dto.user.response.LoginResponse;
import com.example.workflow.dto.user.response.UserResponse;
import com.example.workflow.dto.user.response.UserSessionResponse;
import com.example.workflow.dto.user.response.UsersByRoleResponse;
import com.example.workflow.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import com.example.workflow.services.UserService;

import java.util.List;
import java.util.Map;
import java.util.Optional;


@Slf4j
@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {

    UserService userService;

    @Operation(summary = "create new user")
    @PostMapping("/register")
    ApiResponse<UserResponse> register(@RequestBody @Valid RegistrationRequest request) {
        return ApiResponse.<UserResponse>builder()
                .code(200)
                .result(userService.register(request))
                .message("Vui lòng xác nhận email")
                .build();
    }

    @Operation(summary = "get all user")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/users")
    ApiResponse<List<User>> getAllProfiles() {
        return ApiResponse.<List<User>>builder()
                .code(200)
                .message("Ok")
                .result(userService.getAllProfiles())
                .build();
    }
    @Operation(summary = "get user by id")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("user/{id}")
    ApiResponse<Optional<User>> getProfile(@PathVariable String id) {
        return ApiResponse.<Optional<User>>builder()
                .code(200)
                .message("Ok")
                .result(userService.getUserById(id))
                .build();
    }


    @PostMapping("/login")
    ApiResponse<LoginResponse> login(@RequestBody @Valid LoginRequest request) {
        return ApiResponse.<LoginResponse>builder()
                .code(200)
                .message("Ok")
                .result(userService.login(request))
                .build();
    }

    @PutMapping("/user/update")
    public ResponseEntity<ApiResponse<String>> updateUser(
            @RequestBody @Valid UpdateUserRequest request) {
        try {
            userService.UpdateUser(request);
            ApiResponse<String> response = ApiResponse.<String>builder()
                    .code(200)
                    .message("Ok")
                    .build();
            return ResponseEntity.ok(response);
        } catch (Exception ex) {

            ApiResponse<String> response = ApiResponse.<String>builder()
                    .code(500)
                    .message("Error updating user")
                    .result(ex.getMessage())
                    .build();
            return ResponseEntity.status(500).body(response);
        }
    }


    @Operation(summary = "change password after passing OTP ")
    @PutMapping("/change-password")
    public ApiResponse<String> changePassword(@RequestBody @Valid ChangePasswordRequest request) {
        return ApiResponse.<String>builder()
                .code(200)
                .result(userService.changePassword(request))
                .message("Ok")
                .build();
    }

    @PostMapping("/logout")
    public void logout() {
        userService.logout();
        log.info("Logout successful");
    }

    @Operation(summary = "get list user in session")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/user-in-session")
    public ApiResponse<List<UserSessionResponse>> getUserInSession() {
        return ApiResponse.<List<UserSessionResponse>>builder()
                .code(200)
                .message("Ok")
                .result(userService.getAllUsersSession())
                .build();
    }

    @Operation(summary = "set role user")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/set-role/{id}/{role}")
    public ApiResponse<String> setRole(@PathVariable String id, @PathVariable String role) {
        return ApiResponse.<String>builder()
                .code(200)
                .result(userService.setRoleUser(id, role))
                .message("Ok")
                .build();
    }

    @Operation(summary = "remove role user")
    @PostMapping("/remove-role/{id}/{role}")
    public ApiResponse<String> removeRole(@PathVariable String id, @PathVariable String role) {
        return ApiResponse.<String>builder()
                .code(200)
                .result(userService.removeRoleUser(id, role))
                .message("Ok")
                .build();
    }

    @Operation(summary = "get list user by role")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/users/{role}")
    public ApiResponse<List<UsersByRoleResponse>> getUserListByRole(@PathVariable String role) {
        return ApiResponse.<List<UsersByRoleResponse>>builder()
                .code(200)
                .result(userService.getUsersByRole(role))
                .message("Users By Role successfully")
                .build();
    }

}

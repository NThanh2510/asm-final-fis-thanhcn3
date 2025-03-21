package com.example.workflow.services;

import com.example.workflow.dto.user.request.*;
import com.example.workflow.dto.user.response.LoginResponse;
import com.example.workflow.dto.user.response.UserResponse;
import com.example.workflow.dto.user.response.UserSessionResponse;
import com.example.workflow.dto.user.response.UsersByRoleResponse;
import com.example.workflow.entity.User;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;
import java.util.Optional;

public interface UserService{

    public List<User> getAllProfiles();

    public Optional<User> getUserById(String id);

    public UserResponse register(RegistrationRequest request);

    public LoginResponse login (LoginRequest response);

    public void  UpdateUser(UpdateUserRequest request);

    public String changePassword(ChangePasswordRequest request);

    public void logout();

    public List<UserSessionResponse> getAllUsersSession();

    public String setRoleUser(String id, String roleName);

    public List<UsersByRoleResponse> getUsersByRole(String role);

    public String removeRoleUser(String id, String roleName);
}


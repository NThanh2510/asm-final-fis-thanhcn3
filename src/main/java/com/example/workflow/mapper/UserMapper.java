package com.example.workflow.mapper;

import com.example.workflow.dto.user.request.RegistrationRequest;
import com.example.workflow.dto.user.response.UserResponse;
import com.example.workflow.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(RegistrationRequest request);
    UserResponse toUserResponse(User user);
}

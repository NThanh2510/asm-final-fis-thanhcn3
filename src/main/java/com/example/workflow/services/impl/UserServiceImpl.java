package com.example.workflow.services.impl;

import com.example.workflow.dto.user.identity.TokenExchangeParam;
import com.example.workflow.dto.user.identity.TokenLoginExchangeParam;
import com.example.workflow.dto.user.request.*;
import com.example.workflow.dto.user.response.*;
import com.example.workflow.entity.User;
import com.example.workflow.exception.AppException;
import com.example.workflow.exception.ErrorCode;
import com.example.workflow.exception.ErrorNormalizer;
import feign.FeignException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import com.example.workflow.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.workflow.reponsitory.IdentityClient;
import com.example.workflow.reponsitory.UserRepository;
import com.example.workflow.services.UserService;
import spinjar.com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService {

    UserRepository userRepository;
    UserMapper userMapper;
    IdentityClient identityClient;
    ErrorNormalizer errorNormalizer;

    @Value("${idp.client-secret}")
    @NonFinal
    String clientSecret;

    @Value("${idp.client-id}")
    @NonFinal
    String clientId;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public List<User> getAllProfiles() {
        List<User> users = userRepository.findAll();
        log.info("users: {}", users);
        return users;
    }

    @Override
    public Optional<User> getUserById(String id) {
        Optional<User> user = Optional.ofNullable(userRepository.findByKcid(id).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_EXISTED)));
        return user;
    }

    @Override
    public UserResponse register(RegistrationRequest request) {

        var token = identityClient.exchangeToken(TokenExchangeParam.builder()
                .grant_type("client_credentials")
                .client_id(clientId)
                .client_secret(clientSecret)
                .scope("openid")
                .build());
        log.info("Token: {}", token);


        userRepository.findByEmail(request.getEmail())
                .ifPresent(user -> {
                    throw new AppException(ErrorCode.EMAIL_EXISTED);
                });

        userRepository.findByUsername(request.getUsername())
                .ifPresent(user -> {
                    throw new AppException(ErrorCode.USER_EXISTED);
                });

        User newUser = new User();
        newUser.setUsername(request.getUsername());
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));
        newUser.setEmail(request.getEmail());
        newUser.setFirstName(request.getFirstName());
        newUser.setLastName(request.getLastName());
        newUser.setDob(LocalDate.now());
        String kcid = "f:455db5c1-9cb1-4edf-af6a-11d0974980ca:";
        newUser = userRepository.save(newUser);


        newUser.setKcid(kcid + newUser.getId()); // Gán giá trị kcid dựa trên ID
        newUser = userRepository.save(newUser);
        identityClient.verifyMaill("Bearer " + token.getAccessToken(), kcid+newUser.getId());
        return null;
    }

    @Override
    public LoginResponse login(LoginRequest request) {

        Optional<User> user = userRepository.findByUsername(request.getUsername());
        if (user.isEmpty()) {
            System.out.println("Không tìm thấy người dùng");
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        } else {
            String storedPassword = user.get().getPassword();
            if (passwordEncoder.matches(request.getPassword(), storedPassword)) {
                System.out.println("Mật khẩu đúng");
                try {
                    var token = identityClient.exchangeTokenLogin(TokenLoginExchangeParam.builder()
                            .grant_type("password")
                            .client_id(clientId)
                            .client_secret(clientSecret)
                            .username(request.getUsername())
                            .password(request.getPassword())
                            .scope("openid")
                            .build());
                    log.info("Token response: {}", token.getAccessToken());

                    return LoginResponse.builder()
                            .accessToken(token.getAccessToken())
                            .refreshToken(token.getRefreshToken())
                            .expiresIn(token.getExpiresIn())
                            .build();
                } catch (FeignException exception) {
                    String errorMessage = exception.contentUTF8();
                    log.error("Feign Exception occurred: {}", errorMessage);
                    throw new AppException(ErrorCode.VERIFY_MAIL);
                }
            } else {
                System.out.println("Mật khẩu sai");
                throw new AppException(ErrorCode.USERAPASS_NOT_EXISTED);
            }
        }
    }


    @Override
    public void UpdateUser(UpdateUserRequest request) {
        try {
            var authentication = SecurityContextHolder.getContext().getAuthentication();
            String kcUserId = authentication.getName();
            log.info("KcUserId: {}", kcUserId);
            User user = userRepository.findByKcid(kcUserId).orElseThrow();

            user.setFirstName(request.getFirstName());
            user.setLastName(request.getLastName());
            user.setEmail(request.getEmail());

            userRepository.save(user);
        } catch (FeignException exception) {
            throw errorNormalizer.handleKeyCloakException(exception);
        }
    }


    @Override
    public String changePassword(ChangePasswordRequest request) {
        log.info("Change password request: {}", request);
        var user = userRepository.findByEmail(request.getEmail()).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_EXISTED));
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
        log.info(user.getPassword());
        return null;
    }

    @Override
    public void logout() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        String kcUserId = authentication.getName();
        identityClient.logOut( kcUserId);
        log.info("KcUserId: {}", kcUserId);
    }

    @Override
    public List<UserSessionResponse> getAllUsersSession() {
        var token = identityClient.exchangeToken(TokenExchangeParam.builder()
                .grant_type("client_credentials")
                .client_id(clientId)
                .client_secret(clientSecret)
                .scope("openid")
                .build());
        List<SessionResponse> sessionResponses = identityClient.getAllUserSessions("Bearer " + token.getAccessToken());

        List<String> userIds = sessionResponses.stream()
                .map(SessionResponse::getUserId)
                .collect(Collectors.toList());

        List<UserSessionResponse> userOnline = userRepository.findUserSessionByKcid(userIds);
        List<UserSessionResponse> users = userRepository.findAllUserByKcid();

        List<UserSessionResponse> filteredUsers = users.stream()
                .filter(user -> !userIds.contains(user.getKcid()))
                .toList();

        List<UserSessionResponse> allUsers = new ArrayList<>(userOnline);
        allUsers.addAll(filteredUsers);

        return allUsers;
    }

    @Override
    public String setRoleUser(String kcid, String roleName) {
        try {
            var token = identityClient.exchangeToken(TokenExchangeParam.builder()
                    .grant_type("client_credentials")
                    .client_id(clientId)
                    .client_secret(clientSecret)
                    .scope("openid")
                    .build());

           var response = identityClient.getRoleIdByName("Bearer " + token.getAccessToken(), roleName);
            List<RoleMappingRequest> requests = new ArrayList<>();
            requests.add(new RoleMappingRequest(response.getId(), response.getName()));

            ObjectMapper objectMapper = new ObjectMapper();
            String jsonRequest = objectMapper.writeValueAsString(requests);

            identityClient.addRoleMapping("Bearer " + token.getAccessToken(), kcid, requests);

        } catch (FeignException e) {
            log.error("Feign Exception: {}", e.getMessage());
            return "FeignException occurred: " + e.getMessage(); // Trả về thông báo lỗi chi tiết
        } catch (Exception e) {
            log.error("Unexpected error", e);
            return "Unexpected error occurred: " + e.getMessage();
        }
        log.info("Them role thanh cong", kcid);
        return null;
    }

    @Override
    public List<UsersByRoleResponse> getUsersByRole(String role) {
        var token = identityClient.exchangeToken(TokenExchangeParam.builder()
                    .grant_type("client_credentials")
                    .client_id(clientId)
                    .client_secret(clientSecret)
                    .scope("openid")
                    .build());

        List<UsersByRoleResponse> users = identityClient.getUsersByRole("Bearer " + token.getAccessToken(), role);
        return users;
    }

    @Override
    public String removeRoleUser(String kcid, String roleName) {
        try {
            var token = identityClient.exchangeToken(TokenExchangeParam.builder()
                    .grant_type("client_credentials")
                    .client_id(clientId)
                    .client_secret(clientSecret)
                    .scope("openid")
                    .build());

            var response = identityClient.getRoleIdByName("Bearer " + token.getAccessToken(), roleName);
            List<RoleMappingRequest> requests = new ArrayList<>();
            requests.add(new RoleMappingRequest(response.getId(), response.getName()));

            ObjectMapper objectMapper = new ObjectMapper();
            String jsonRequest = objectMapper.writeValueAsString(requests);

            identityClient.removeRoleMapping("Bearer " + token.getAccessToken(), kcid, requests);

        } catch (FeignException e) {
            log.error("Feign Exception: {}", e.getMessage());
            return "FeignException occurred: " + e.getMessage(); // Trả về thông báo lỗi chi tiết
        } catch (Exception e) {
            log.error("Unexpected error", e);
            return "Unexpected error occurred: " + e.getMessage();
        }
        log.info("Xoa role thanh cong", kcid);
        return null;
    }


}

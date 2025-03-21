package com.example.workflow.reponsitory;


import com.example.workflow.configuration.FeignConfig;
import com.example.workflow.dto.user.identity.*;
import com.example.workflow.dto.user.request.RoleMappingRequest;
import com.example.workflow.dto.user.request.UpdateUserRequest;
import com.example.workflow.dto.user.response.SessionResponse;
import com.example.workflow.dto.user.response.UsersByRoleResponse;
import com.example.workflow.entity.User;
import feign.QueryMap;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Component
@FeignClient(name = "identity-client", url = "${idp.url}", configuration = FeignConfig.class)
public interface IdentityClient {
    @PostMapping(value = "/realms/keycloak_demo/protocol/openid-connect/token",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    TokenExchangeResponse exchangeToken(@QueryMap TokenExchangeParam param);

    @PostMapping(value = "/realms/keycloak_demo/protocol/openid-connect/token",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    TokenExchangeResponse exchangeTokenLogin(@QueryMap TokenLoginExchangeParam param);

    @PostMapping(value = "/admin/realms/keycloak_demo/users",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<?> createUser(
            @RequestHeader("authorization") String token,
            @RequestBody UserCreationParam param);

    @PutMapping("/admin/realms/keycloak_demo/users/{id}")
    void updateUser(@RequestHeader("Authorization") String token,
                    @PathVariable("id") String userId,
                    @RequestBody UpdateUserRequest request);

    @PostMapping("/admin/realms/keycloak_demo/users/{id}/logout")
    void logOut(
            @PathVariable("id") String id);

    @GetMapping("/admin/realms/keycloak_demo/users")
    List<KeycloakUser> searchUser(
            @RequestHeader("Authorization") String bearerToken,
            @RequestParam("search") String username);

    @PutMapping("/admin/realms/keycloak_demo/users/{id}/send-verify-email")
    void verifyMaill(@RequestHeader("Authorization") String token,
                     @PathVariable("id") String userId);

    @GetMapping("/admin/realms/keycloak_demo/clients/cd25086e-8bf2-49f3-b153-42ca4a2ce784/user-sessions")
    List<SessionResponse> getAllUserSessions(
            @RequestHeader("Authorization") String token
    );

    @GetMapping("admin/realms/keycloak_demo/roles/{name}")
    RoleMappingRequest getRoleIdByName(
            @RequestHeader("Authorization") String token,
            @PathVariable("name") String name
    );

    @PostMapping(value = "/admin/realms/keycloak_demo/users/{id}/role-mappings/realm")
    void addRoleMapping(
            @RequestHeader("Authorization") String token,
            @PathVariable("id") String id,
            @RequestBody List<RoleMappingRequest> roleInfo
    );

    @DeleteMapping(value = "/admin/realms/keycloak_demo/users/{id}/role-mappings/realm")
    void removeRoleMapping(
            @RequestHeader("Authorization") String token,
            @PathVariable("id") String id,
            @RequestBody List<RoleMappingRequest> roleInfo
    );

    @GetMapping("/admin/realms/keycloak_demo/roles/{role}/users")
    List<UsersByRoleResponse> getUsersByRole(
            @RequestHeader("Authorization") String token,
            @PathVariable("role") String role
    );
}
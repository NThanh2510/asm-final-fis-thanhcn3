package com.example.workflow.reponsitory;

import com.example.workflow.dto.user.response.UserSessionResponse;
import com.example.workflow.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);
    Optional<User> findByKcid(String kcid);



    @Query("UPDATE User u SET u.kcid = :kcid WHERE u.id = :userId")
    void updateKcidById(@Param("userId") Long userId, @Param("kcid") String kcid);
    @Query("SELECT new com.example.workflow.dto.user.response.UserSessionResponse(" +
            "u.username, u.lastName, u.firstName, u.email, u.kcid, true ) " +
            "FROM User u " +
            "WHERE u.kcid IN  :userId")
    List<UserSessionResponse> findUserSessionByKcid(@Param("userId") List<String> userId);

    @Query("SELECT new com.example.workflow.dto.user.response.UserSessionResponse(" +
            "u.username, u.lastName, u.firstName, u.email, u.kcid, false ) " +
            "FROM User u " +
            "")
    List<UserSessionResponse> findAllUserByKcid();

    String kcid(String kcid);
}

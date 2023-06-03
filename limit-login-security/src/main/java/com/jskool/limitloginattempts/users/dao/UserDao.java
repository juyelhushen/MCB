package com.jskool.limitloginattempts.users.dao;

import com.jskool.limitloginattempts.users.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface UserDao extends JpaRepository<User,Long> {
        @Query("SELECT u FROM User u WHERE u.email =:email")
        User findUserByEmail(String email);

        @Query("SELECT u FROM User u WHERE u.accountNonLocked = false AND u.lockTime < :currentTime")
        List<User> findExpiredLockedUsers(@Param("currentTime") LocalDateTime currentTime);


        @Transactional
        @Query("UPDATE User u SET u.enabled = ?2 WHERE u.id = ?1")
        @Modifying
        public void updateEnableStatus(@Param("1") Long id , @Param("enabled") boolean enabled);

        @Transactional
        @Query("UPDATE User u SET u.failedAttempt = :failedAttempt WHERE u.email = :email")
        @Modifying
        public void updateFailedAttempt(int failedAttempt, String email);
}

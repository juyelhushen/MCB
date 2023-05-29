package com.mcb.users.repository;

import com.mcb.users.model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    User findByEmail(String email);

//    @Query("SELECT new com.library.management.users.payload.UserResponse(u.id,u.firstname,u.lastname,u.email,u.mobilenumber,u.locked,u.enabled,u.fine,u.role,u.createdOn) FROM User u")
//    List<UserResponse> getAllUsers();

    @Transactional
    @Modifying
    @Query("UPDATE User u SET enabled = false WHERE u.id = :id")
    void disableUser(long id);

    @Transactional
    @Modifying
    @Query("UPDATE User u SET nonLocked = false WHERE u.id = :id")
    void blockUser(long id);

    @Query("UPDATE User u SET u.failedAttempt = :failedAttempt WHERE u.email = :email")
    @Modifying(clearAutomatically = true)
    @Transactional
    public void updateFailedAttempt(int failedAttempt,@PathVariable("email") String email);

}

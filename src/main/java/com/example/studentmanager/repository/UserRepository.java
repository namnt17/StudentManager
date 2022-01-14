package com.example.studentmanager.repository;


import com.example.studentmanager.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value = "SELECT u FROM User u WHERE u.username = :username")
    User getUserByUsername(@Param("username") String username);

    @Modifying
    @Query(value = "update users set enabled = true where user_id = :id", nativeQuery = true)
    void verifySuccessAccount(@Param("id") Long id);

    @Query("SELECT u FROM User u WHERE u.verification_code = :code")
    User findByVerificationCode(@Param("code") String code);


}

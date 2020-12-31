package com.inspire12.homepage.domain.repository;

import com.inspire12.homepage.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface UserRepository extends JpaRepository <User, String>{
    void deleteByUsername(String username);
    User findByUsernameAndPassword(String username, String password);

    @Modifying
    @Transactional
    @Query(value = "UPDATE users SET last_logined_at = NOW() where username=:username", nativeQuery = true)
    void updateUserLastLoginTime(@Param("username") String username);

    @Modifying
    @Transactional
    @Query(value = "UPDATE users SET password=:password where username=:username", nativeQuery = true)
    int updateNewPassword(@Param("username") String username, @Param("password")String password);
}

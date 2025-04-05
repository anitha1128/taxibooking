package com.taxi.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.taxi.model.Admin;

public interface AdminDao extends JpaRepository<Admin, Integer> {

    Optional<Admin> findByUsername(String username);

    @Modifying
    @Transactional
    @Query("UPDATE Admin a SET a.username = :newusername, a.password = :newpassword WHERE a.username = :oldusername")
    int updateCredentials(
        @Param("newusername") String newusername,
        @Param("newpassword") String newpassword,
        @Param("oldusername") String oldusername
    );
}

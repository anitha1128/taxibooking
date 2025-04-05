package com.taxi.config;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.taxi.dao.AdminDao;
import com.taxi.model.Admin;

import jakarta.annotation.PostConstruct;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    private final AdminDao adminDao;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserDetailServiceImpl(PasswordEncoder passwordEncoder, AdminDao adminDao) {
        this.passwordEncoder = passwordEncoder;
        this.adminDao = adminDao;
    }

    @PostConstruct
    public void init() {
        long count = adminDao.count();
        if (count == 0) { // If no admin exists, create one
            Admin admin = new Admin();
            admin.setUsername("admin"); // Set the username
            admin.setPassword(passwordEncoder.encode("admin123")); // Store hashed password
            adminDao.save(admin); // Save the admin in the database
        }
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin admin = adminDao.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Admin doesn't exist"));

        return User.withUsername(admin.getUsername())
                   .password(admin.getPassword())  // ✅ Already hashed
                   .roles("ADMIN")  // ✅ Assign role
                   .build();
    }
}

package com.taxi.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.taxi.dao.AdminDao;
import com.taxi.model.Admin;

@Service
public class AdminCredentailsServiceImpl implements AdminCredentialsService {  // Kept the name as per your request

    private final AdminDao adminDao;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AdminCredentailsServiceImpl(AdminDao adminDao, PasswordEncoder passwordEncoder) {
        this.adminDao = adminDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String checkAdminCredentials(String oldusername, String oldpassword) {
        Optional<Admin> adminOptional = adminDao.findByUsername(oldusername);

        if (adminOptional.isPresent()) {
            Admin admin = adminOptional.get();
            if (passwordEncoder.matches(oldpassword, admin.getPassword())) {
                return "SUCCESS";  // Ensure this matches the controller check
            } else {
                return "Wrong Credentials";
            }
        } else {
            return "Wrong Credentials";
        }
    }

    @Transactional  // Ensure the update happens within a transaction
    @Override
    public String updateAdminCredentials(String newusername, String newpassword, String oldusername) {
        System.out.println("🔄 Attempting to update credentials...");
        System.out.println("Old Username: " + oldusername);
        System.out.println("New Username: " + newusername);
        System.out.println("New Password (Before Encoding): " + newpassword);

        Optional<Admin> existingAdmin = adminDao.findByUsername(oldusername);
        if (existingAdmin.isEmpty()) {
            System.out.println("❌ Old username not found!");
            return "Old username does not exist!";
        }

        String encodedPassword = passwordEncoder.encode(newpassword);
        System.out.println("New Password (Encoded): " + encodedPassword);

        int updateCount = adminDao.updateCredentials(newusername, encodedPassword, oldusername);
        System.out.println("🔍 Rows Affected: " + updateCount);

        return (updateCount == 1) ? "CREDENTIALS UPDATED SUCCESSFULLY" : "FAILED TO UPDATE";
    }
}

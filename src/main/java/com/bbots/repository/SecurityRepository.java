package com.bbots.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class SecurityRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Map<String, Object> findUserForAuth(String email) {
        try {
            System.out.println("🔍 SecurityRepository: Querying authentication data for email: " + email);
            
            // Query USERS001 table directly as it contains both email and password hash
            String sql = "SELECT email AS username, password AS password, 'ROLE_USER' AS roles " +
                         "FROM USERS001 " +
                         "WHERE email = ?";
            
            Map<String, Object> result = jdbcTemplate.queryForMap(sql, email);
            System.out.println("✅ SecurityRepository: User found in database.");
            return result;
        } catch (EmptyResultDataAccessException e) {
            System.out.println("❌ SecurityRepository: No user found for email: " + email);
            return null;
        } catch (Exception e) {
            System.err.println("🔥 SecurityRepository: Database error during authentication: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
}

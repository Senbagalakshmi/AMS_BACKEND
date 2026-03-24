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
            // Fetch password and basic info from USERS001
            // The user confirmed email and password are in users001
            return jdbcTemplate.queryForMap(
                "SELECT email as username, password as password, 'ROLE_USER' as roles FROM USERS001 WHERE email = ?", 
                email
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}

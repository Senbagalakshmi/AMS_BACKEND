package com.bbots.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationProcedureService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Calls the pr_handle_authorization PostgreSQL Procedure.
     * 
     * @param orgCode     Organization code (e.g. "ORG01" or "101")
     * @param programId   Program ID (e.g. "USR-CRT", "ROLE-CRT")
     * @param tableName   Target table name (e.g. "USERS001", "ROLE001")
     * @param payload     The DTO / Entity to be serialized to JSON
     */
    public void processAuthorization(String orgCode, String programId, String tableName, Object payload) {
        String sql = "CALL pr_handle_authorization(?::varchar, ?::varchar, ?::varchar, ?::json)";
        try {
            String jsonPayload = objectMapper.writeValueAsString(payload);
            jdbcTemplate.update(sql, orgCode, programId, tableName, jsonPayload);
            System.out.println("Successfully called auth procedure for: " + programId);
        } catch (JsonProcessingException e) {
            System.err.println("Error serializing payload for auth procedure: " + e.getMessage());
            throw new RuntimeException("Serialization failed", e);
        } catch (Exception e) {
            System.err.println("Error calling auth procedure: " + e.getMessage());
            throw new RuntimeException("Authorization procedure execution failed", e);
        }
    }
}

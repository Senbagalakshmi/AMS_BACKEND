package com.bbots.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.bbots.model.Auth101Config;
import com.bbots.model.AuthRecord;
import com.bbots.service.AuthService;
import com.bbots.service.UserService;
import com.bbots.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;
import java.util.List;
import com.bbots.model.AuthConfigDTO;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private AuthService service;

    @Autowired
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping("/configs")
    public List<Auth101Config> getConfigs() {
        return service.getAllConfigs();
    }

    @PostMapping("/configs")
    public Auth101Config updateConfig(@RequestBody Auth101Config cfg) {
        return service.updateConfig(cfg);
    }

    @GetMapping("/authctl/list")
    public List<AuthConfigDTO> getAuthConfigs() {
        return service.getAllAuthConfigs();
    }

    @PostMapping("/authctl/create")
    public void createAuthConfig(@RequestBody AuthConfigDTO dto) {
        service.createAuthConfig(dto);
    }

    @GetMapping("/queue")
    public List<AuthRecord> getQueue() {
        return service.getAuthQueue();
    }

    @PostMapping("/approve/{authSl}")
    public void approve(@PathVariable Long authSl, @RequestParam int level, @RequestParam String userId) {
        service.approve(authSl, level, userId);
    }

    @PostMapping("/reject/{authSl}")
    public void reject(@PathVariable Long authSl, @RequestParam int level, @RequestParam String userId) {
        service.reject(authSl, level, userId);
    }

    @ExceptionHandler(Exception.class)
    public org.springframework.http.ResponseEntity<String> handleExceptions(Exception e) {
        e.printStackTrace();
        return org.springframework.http.ResponseEntity.status(500).body(e.getMessage());
    }
}

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
class Auth002Controller {

    @Autowired
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @PostMapping("/auth002")
    public void submitAuth002(@RequestBody Map<String, Object> payload) {
        try {
            // Check what program it is. Currently we only handle USR-CRT here based on flutter app
            String programId = (String) payload.get("PROGRAMID");
            
            if ("USR-CRT".equals(programId)) {
                // Get the DATABLOCK which is the User JSON
                String datablock = (String) payload.get("DATABLOCK");
                User user = objectMapper.readValue(datablock, User.class);
                userService.createUserAuthRequest(user);
            } else {
                // If it's something else, we could handle it here or throw an error
                throw new UnsupportedOperationException("Program ID " + programId + " not supported on this endpoint yet.");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error processing Auth002 submission", e);
        }
    }
}

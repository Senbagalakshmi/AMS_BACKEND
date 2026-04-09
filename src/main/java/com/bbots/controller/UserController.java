package com.bbots.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.bbots.dto.UserProfileDTO;
import com.bbots.util.JwtUtil;
import javax.servlet.http.HttpServletRequest;
import com.bbots.model.User;
import com.bbots.service.UserService;
import com.bbots.service.AuthorizationProcedureService;

import java.util.Map;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

	@Autowired
	private JwtUtil jwtUtil;
	
    @Autowired
    private UserService service;

    @Autowired
    private AuthorizationProcedureService authProcedureService;

    @GetMapping
    public Map<String, Object> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return service.getPaginatedUsers(page, size);
    }

    @GetMapping("/{userscd}")
    public User getUser(@PathVariable String userscd, @RequestParam(required = false, defaultValue = "50") Long orgcode) {
        return service.getUserById(orgcode, userscd);
    }

    @PostMapping
    public void createUser(@RequestBody User user) {
        if (user.getOrgcode() == null) {
            user.setOrgcode(50L);
        }
        // Table name is now USER001 for user information
        authProcedureService.processAuthorization(user.getOrgcode(), "USR-CRT", "USER001", user);
    }

    @PutMapping
    public void updateUser(@RequestBody User user) {
        service.updateUser(user);
    }

    @DeleteMapping("/{userscd}")
    public void deleteUser(@PathVariable String userscd, @RequestParam(required = false, defaultValue = "50") Long orgcode) {
        service.deleteUser(orgcode, userscd);
    }
    @GetMapping("/profile")
    public UserProfileDTO getProfile(HttpServletRequest request) {

        String authHeader = request.getHeader("Authorization");
        String token = authHeader.substring(7);
        String username = jwtUtil.extractUsername(token);

        return service.getUserProfileByUsername(username);
    }
}

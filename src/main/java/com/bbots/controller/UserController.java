package com.bbots.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.bbots.dto.UserProfileDTO;
import com.bbots.model.User;
import com.bbots.service.UserService;
import com.bbots.util.JwtUtil;
import com.bbots.service.AuthorizationProcedureService;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
    public List<User> getAllUsers() {
        return service.getAllUsers();
    }

    @GetMapping("/{userscd}")
    public User getUser(@PathVariable String userscd) {
        return service.getUserById(userscd);
    }

    @PostMapping
    public void createUser(@RequestBody User user) {
        String orgCode = user.getOrgCode() != null ? String.valueOf(user.getOrgCode()) : "101";
        authProcedureService.processAuthorization(orgCode, "USR-CRT", "USERS001", user);
    }

    @PutMapping
    public void updateUser(@RequestBody User user) {
        service.updateUser(user);
    }

    @DeleteMapping("/{userscd}")
    public void deleteUser(@PathVariable String userscd) {
        service.deleteUser(userscd);
    }
    @GetMapping("/profile")
    public UserProfileDTO getProfile(HttpServletRequest request) {

        String authHeader = request.getHeader("Authorization");
        String token = authHeader.substring(7);
        String username = jwtUtil.extractUsername(token);

        return service.getUserProfileByUsername(username);
    }
    
}

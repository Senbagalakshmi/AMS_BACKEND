package com.bbots.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.bbots.dto.UserProfileDTO;
import com.bbots.util.JwtUtil;
import javax.servlet.http.HttpServletRequest;
import com.bbots.model.User;
import com.bbots.service.UserService;
import com.bbots.service.AuthorizationProcedureService;

import java.util.List;

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
    public User getUser(@PathVariable Long userscd) {
        return service.getUserById(userscd);
    }

    @PostMapping
    public void createUser(@RequestBody User user) {
        if (user.getOrgcode() == null) {
            user.setOrgcode(50L);
        }
        authProcedureService.processAuthorization(user.getOrgcode(), "USR-CRT", "USERS001", user);
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

package com.bbots.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.bbots.model.User;
import com.bbots.service.UserService;
import com.bbots.service.AuthorizationProcedureService;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

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
}

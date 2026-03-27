package com.bbots.controller;

import com.bbots.model.Role;
import com.bbots.service.RoleService;
import com.bbots.service.AuthorizationProcedureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
@CrossOrigin(origins = "*")
public class RoleController {

    @Autowired
    private RoleService service;

    @Autowired
    private AuthorizationProcedureService authProcedureService;

    @GetMapping
    public List<Role> getAllRoles() {
        return service.getAllRoles();
    }

    @GetMapping("/{roleCd}")
    public Role getRole(@PathVariable Integer roleCd) {
        return service.getRoleById(roleCd);
    }

    @PostMapping
    public void createRole(@RequestBody Role role) {
        if (role.getOrgcode() == null) {
            role.setOrgcode(50L);
        }
        authProcedureService.processAuthorization(role.getOrgcode(), "ROLE-CRT", "ROLE001", role);
    }

    @PutMapping
    public void updateRole(@RequestBody Role role) {
        service.updateRole(role);
    }

    @DeleteMapping("/{roleCd}")
    public void deleteRole(@PathVariable Integer roleCd) {
        service.deleteRole(roleCd);
    }
}

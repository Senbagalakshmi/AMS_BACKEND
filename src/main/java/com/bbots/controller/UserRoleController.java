package com.bbots.controller;

import com.bbots.model.UserRoleAssign;
import com.bbots.service.UserRoleService;
import com.bbots.service.AuthorizationProcedureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user-roles")
@CrossOrigin(origins = "*")
public class UserRoleController {

    @Autowired
    private UserRoleService service;

    @Autowired
    private AuthorizationProcedureService authProcedureService;

    @GetMapping
    public List<UserRoleAssign> getAll() {
        return service.getAllAssignments();
    }

    @GetMapping("/{userScd}")
    public List<UserRoleAssign> getByUserId(@PathVariable String userScd) {
        return service.getAssignmentsByUserId(userScd);
    }

    @PostMapping
    public void assign(@RequestBody UserRoleAssign ura) {
        if (ura.getOrgcode() == null) {
            ura.setOrgcode(50L);
        }
        authProcedureService.processAuthorization(ura.getOrgcode(), "USR-ROLE", "USERS002", ura);
    }

    @DeleteMapping("/{userScd}/{roleCd}")
    public void revoke(@PathVariable String userScd, @PathVariable Integer roleCd) {
        service.revokeRole(userScd, roleCd);
    }
}

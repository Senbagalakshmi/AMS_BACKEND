package com.bbots.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.bbots.model.Branch;
import com.bbots.service.BranchService;
import com.bbots.service.AuthorizationProcedureService;

import java.util.Map;

@RestController
@RequestMapping("/api/branches")
@CrossOrigin(origins = "*")
public class BranchController {

    @Autowired
    private BranchService service;

    @Autowired
    private AuthorizationProcedureService authProcedureService;

    // GET all branches with pagination
    // GET /api/branches?page=0&size=10
    @GetMapping
    public Map<String, Object> getAllBranches(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return service.getPaginatedBranches(page, size);
    }

    // GET single branch by brncd
    // GET /api/branches/101?orgcode=50
    @GetMapping("/{brncd}")
    public Branch getBranch(
            @PathVariable Long brncd,
            @RequestParam(required = false, defaultValue = "50") Long orgcode) {
        return service.getBranchById(orgcode, brncd);
    }

    // POST - create new branch (goes through authorization)
    // POST /api/branches
    @PostMapping
    public void createBranch(@RequestBody Branch branch) {
        if (branch.getOrgcode() == null) {
            branch.setOrgcode(50L);
        }
        authProcedureService.processAuthorization(branch.getOrgcode(), "BRN-CRT", "BRANCH001", branch);
    }

    // PUT - update existing branch
    // PUT /api/branches
    @PutMapping
    public void updateBranch(@RequestBody Branch branch) {
        service.updateBranch(branch);
    }

    // DELETE - delete branch by brncd
    // DELETE /api/branches/101?orgcode=50
    @DeleteMapping("/{brncd}")
    public void deleteBranch(
            @PathVariable Long brncd,
            @RequestParam(required = false, defaultValue = "50") Long orgcode) {
        service.deleteBranch(orgcode, brncd);
    }
}
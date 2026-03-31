package com.bbots.gl.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bbots.gl.model.GlBranch;
import com.bbots.gl.service.GlBranchService;
import com.bbots.service.AuthorizationProcedureService;

@RestController
@RequestMapping("/api/gl-branch")
@CrossOrigin(origins = "*")
public class GlBranchController {
	
	
	@Autowired
    private GlBranchService glBranchService;
	
	 @Autowired
	private AuthorizationProcedureService authProcedureService;
	
	@GetMapping
    public List<GlBranch> getAll() {
        return glBranchService.getAllGlNumber();
    }

    @GetMapping("/{glNo}")
    public List<GlBranch> getByUserId(@PathVariable Integer glNo) {
        return glBranchService.getAssignmentsByUserId(glNo);
    }

    @PostMapping
    public void create(@RequestBody GlBranch gb) {
    	
    	if (gb.getOrgCode() == null) {
    		gb.setOrgCode(50L);
        }
        authProcedureService.processAuthorization(gb.getOrgCode(), "GL-BRN", "GL104", gb);
    }

    @DeleteMapping("/{glNo}")
    public void revoke(@PathVariable Integer glNo) {
    	glBranchService.revokeRole(glNo);
    }
    
    @PutMapping
    public void update(@RequestBody GlBranch gb) {
    	glBranchService.updateGlBranch(gb);
    }

}

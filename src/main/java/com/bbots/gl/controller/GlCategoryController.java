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

import com.bbots.gl.model.GlCategory;
import com.bbots.gl.service.GlCategoryService;
import com.bbots.service.AuthorizationProcedureService;

@RestController
@RequestMapping("/api/gl-category")
@CrossOrigin(origins = "*")
public class GlCategoryController {
	
	@Autowired
	private GlCategoryService glCategoryService;
	
	 @Autowired
	private AuthorizationProcedureService authProcedureService;
	
	
	@GetMapping
    public List<GlCategory> getAll() {
        return glCategoryService.getAllGlCategory();
    }

    @GetMapping("/{glCatCd}")
    public List<GlCategory> getByUserId(@PathVariable Integer glCatCd) {
        return glCategoryService.getAssignmentsByUserId(glCatCd);
    }

    @PostMapping
    public void create(@RequestBody GlCategory gl) {
    	
    	if (gl.getOrgCode() == null) {
    		gl.setOrgCode(50L);
        }
        authProcedureService.processAuthorization(gl.getOrgCode(), "GL-CAT", "GL101", gl);
    }

    @DeleteMapping("/{glCatCd}")
    public void revoke(@PathVariable Integer glCatCd) {
    	glCategoryService.revokeRole(glCatCd);
    }
    
    @PutMapping
    public void update(@RequestBody GlCategory gl) {
    	glCategoryService.updateGlCategory(gl);
    }

}

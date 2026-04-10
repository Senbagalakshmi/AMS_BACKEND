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

import com.bbots.gl.model.GlMaster;
import com.bbots.gl.service.GlMasterService;
import com.bbots.service.AuthorizationProcedureService;

@RestController
@RequestMapping("/api/gl-master")
@CrossOrigin(origins = "*")
public class GlMasterController {
	
	
	@Autowired
	private GlMasterService glMasterService;
	
	 @Autowired
	private AuthorizationProcedureService authProcedureService;
	
	
	@GetMapping
    public List<GlMaster> getAll() {
        return glMasterService.getAllGlNumber();
    }

    @GetMapping("/{glNo}")
    public List<GlMaster> getByUserId(@PathVariable Integer glNo) {
        return glMasterService.getAssignmentsByUserId(glNo);
    }

    @PostMapping
    public void create(@RequestBody GlMaster gm) {
    	
    	if (gm.getOrgCode() == null) {
    		gm.setOrgCode(50L);
        }
        authProcedureService.processAuthorization(gm.getOrgCode(), "GL-MAT", "GL102", gm);
    }


    @DeleteMapping("/{glNo}")
    public void revoke(@PathVariable Integer glNo) {
    	glMasterService.revokeRole(glNo);
    }
    
    @PutMapping
    public void update(@RequestBody GlMaster gm) {
    	glMasterService.updateGlMaster(gm);
    }

	
	

}

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

import com.bbots.gl.model.GlSegments;
import com.bbots.gl.service.GlSegmentsService;
import com.bbots.service.AuthorizationProcedureService;

@RestController
@RequestMapping("/api/gl-segments")
@CrossOrigin(origins = "*")
public class GlSegmentsController {
	
	@Autowired
    private GlSegmentsService glSegmentsService;
	
	 @Autowired
	private AuthorizationProcedureService authProcedureService;
	
	@GetMapping
    public List<GlSegments> getAll() {
        return glSegmentsService.getAllGlNumber();
    }

    @GetMapping("/{glNo}")
    public List<GlSegments> getByUserId(@PathVariable Integer glNo) {
        return glSegmentsService.getAssignmentsByUserId(glNo);
    }

    @PostMapping
    public void create(@RequestBody GlSegments gs) {
    	
    	if (gs.getOrgCode() == null) {
    		gs.setOrgCode(50L);
        }
        authProcedureService.processAuthorization(gs.getOrgCode(), "GL-SEG", "GL105", gs);
    }


    @DeleteMapping("/{glNo}")
    public void revoke(@PathVariable Integer glNo) {
    	glSegmentsService.revokeRole(glNo);
    }
    
    @PutMapping
    public void update(@RequestBody GlSegments gs) {
    	glSegmentsService.updateGlSegments(gs);
    }


}

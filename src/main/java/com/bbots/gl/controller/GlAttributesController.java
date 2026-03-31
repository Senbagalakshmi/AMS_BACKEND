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
import com.bbots.gl.model.GlAttributes;
import com.bbots.gl.service.GlAttributesService;
import com.bbots.service.AuthorizationProcedureService;


@RestController
@RequestMapping("/api/gl-attributes")
@CrossOrigin(origins = "*")
public class GlAttributesController {
	
	
	@Autowired
    private GlAttributesService glAttributesService;
	 @Autowired
	private AuthorizationProcedureService authProcedureService;
	

	@GetMapping
    public List<GlAttributes> getAll() {
        return glAttributesService.getAllGlNumber();
    }

    @GetMapping("/{glNo}")
    public List<GlAttributes> getByUserId(@PathVariable Integer glNo) {
        return glAttributesService.getAssignmentsByUserId(glNo);
    }

    @PostMapping
    public void create(@RequestBody GlAttributes ga) {  	
    	if (ga.getOrgCode() == null) {
    		ga.setOrgCode(50L);
        }
        authProcedureService.processAuthorization(ga.getOrgCode(), "GL-ATTR", "GL106", ga);
    }

    @DeleteMapping("/{glNo}")
    public void revoke(@PathVariable Integer glNo) {
    	glAttributesService.revokeRole(glNo);
    }
    
    @PutMapping
    public void update(@RequestBody GlAttributes ga) {
    	glAttributesService.updateGlAttributes(ga);
    }


}

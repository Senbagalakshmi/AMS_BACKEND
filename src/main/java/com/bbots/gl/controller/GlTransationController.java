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

import com.bbots.gl.model.GlTransation;
import com.bbots.gl.service.GlTranscationService;



@RestController
@RequestMapping("/api/gl-transcation")
@CrossOrigin(origins = "*")
public class GlTransationController {
	
	
	@Autowired
    private GlTranscationService glTranscationService;
	
	@GetMapping
    public List<GlTransation> getAll() {
        return glTranscationService.getAllGlNumber();
    }

    @GetMapping("/{glNo}")
    public List<GlTransation> getByUserId(@PathVariable Integer glNo) {
        return glTranscationService.getAssignmentsByUserId(glNo);
    }

    @PostMapping
    public void create(@RequestBody GlTransation gt) {
    	glTranscationService.createGlTransation(gt);
    }

    @DeleteMapping("/{glNo}")
    public void revoke(@PathVariable Integer glNo) {
    	glTranscationService.revokeRole(glNo);
    }
    
    @PutMapping
    public void update(@RequestBody GlTransation gt) {
    	glTranscationService.updateGlTransation(gt);
    }

	

}

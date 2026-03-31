package com.bbots.gl.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bbots.gl.model.GlAttributes;
import com.bbots.gl.repository.GlAttributesRepository;

@Service
public class GlAttributesService {
	
	
	@Autowired	
	private GlAttributesRepository glAttributesRepository;
	
	
	public List<GlAttributes> getAllGlNumber() {
        return glAttributesRepository.findAll();
    }

    public List<GlAttributes> getAssignmentsByUserId(Integer glNo) {
        return glAttributesRepository.findByUserId(glNo);
    }

    public void createGlAttributes(GlAttributes ga) {
    	glAttributesRepository.save(ga);
    }

    public void revokeRole(Integer glNo) {
    	glAttributesRepository.delete(glNo);
    }
    
    public void updateGlAttributes(GlAttributes ga) {
    	glAttributesRepository.update(ga);
    }


}

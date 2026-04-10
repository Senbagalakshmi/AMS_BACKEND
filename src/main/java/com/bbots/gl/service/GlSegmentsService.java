package com.bbots.gl.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bbots.gl.model.GlSegments;
import com.bbots.gl.repository.GlSegmentsRepository;

@Service
public class GlSegmentsService {
	
	
	@Autowired	
	private GlSegmentsRepository glSegmentsRepository;
	
	
	public List<GlSegments> getAllGlNumber() {
        return glSegmentsRepository.findAll();
    }

    public List<GlSegments> getAssignmentsByUserId(Integer glNo) {
        return glSegmentsRepository.findByUserId(glNo);
    }

    public void createGlSegments(GlSegments gb) {
    	glSegmentsRepository.save(gb);
    }

    public void revokeRole(Integer glNo) {
    	glSegmentsRepository.delete(glNo);
    }
    
    public void updateGlSegments(GlSegments gb) {
    	glSegmentsRepository.update(gb);
    }


}

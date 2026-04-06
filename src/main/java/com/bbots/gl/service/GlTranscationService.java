package com.bbots.gl.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bbots.gl.model.GlTransation;
import com.bbots.gl.repository.GlTransactionRepository;

@Service
public class GlTranscationService {
	
	@Autowired	
	private GlTransactionRepository glTransactionRepository;
	
	
	public List<GlTransation> getAllGlNumber() {
        return glTransactionRepository.findAll();
    }

    public List<GlTransation> getAssignmentsByUserId(Integer glNo) {
        return glTransactionRepository.findByUserId(glNo);
    }

//    public void createGlTransation(GlTransation gt) {
//        glTransactionRepository.save(gt);
//    }

    public void revokeRole(Long orgCode, Integer glNo) {
        glTransactionRepository.delete(orgCode, glNo);
    }
    
    public void updateGlTransation(GlTransation gt) {
        glTransactionRepository.update(gt);
    }

}

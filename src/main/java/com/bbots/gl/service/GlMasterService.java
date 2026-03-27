package com.bbots.gl.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bbots.gl.model.GlMaster;
import com.bbots.gl.repository.GlMasterRepository;

@Service
public class GlMasterService {
	
	@Autowired
    private  GlMasterRepository GlMasterRepository;
	
	
	public List<GlMaster> getAllGlNumber() {
        return GlMasterRepository.findAll();
    }

    public List<GlMaster> getAssignmentsByUserId(Integer glNo) {
        return GlMasterRepository.findByUserId(glNo);
    }

    public void createGlMaster(GlMaster gm) {
    	GlMasterRepository.save(gm);
    }

    public void revokeRole(Integer glNo) {
    	GlMasterRepository.delete(glNo);
    }
    
    public void updateGlMaster(GlMaster gm) {
    	GlMasterRepository.update(gm);
    }


}

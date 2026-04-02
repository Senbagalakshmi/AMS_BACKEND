package com.bbots.gl.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bbots.gl.model.GlBranch;
import com.bbots.gl.repository.GlBranchRepository;

@Service
public class GlBranchService {
	
	
	@Autowired	
	private GlBranchRepository glBranchRepository;
	
	
	public List<GlBranch> getAllGlNumber() {
        return glBranchRepository.findAll();
    }

    public List<GlBranch> getAssignmentsByUserId(Integer glNo) {
        return glBranchRepository.findByUserId(glNo);
    }

    public void createGlBranch(GlBranch gb) {
    	glBranchRepository.save(gb);
    }

    public void revokeRole(Long orgCode, Integer glNo) {
        glBranchRepository.delete(orgCode, glNo);
    }
    
    public void updateGlBranch(GlBranch gb) {
    	glBranchRepository.update(gb);
    }
    public List<Map<String,Object>> getGlList(){
        return glBranchRepository.getGlList();
    }

}

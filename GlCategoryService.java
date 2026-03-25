package com.bbots.gl.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bbots.gl.model.GlCategory;
import com.bbots.gl.repository.GlCategoryRepository;

@Service
public class GlCategoryService {
	
	@Autowired
    private  GlCategoryRepository glCategoryRepository;
	
	
	public List<GlCategory> getAllGlCategory() {
        return glCategoryRepository.findAll();
    }

    public List<GlCategory> getAssignmentsByUserId(Integer glCatCd) {
        return glCategoryRepository.findByUserId(glCatCd);
    }

    public void createGlCategory(GlCategory gl) {
    	glCategoryRepository.save(gl);
    }

    public void revokeRole(Integer glCatCd) {
    	glCategoryRepository.delete(glCatCd);
    }

}

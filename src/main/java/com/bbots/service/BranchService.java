package com.bbots.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bbots.model.Branch;
import com.bbots.repository.BranchRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BranchService {

    @Autowired
    private BranchRepository repository;

    public List<Branch> getAllBranches() {
        return repository.findAll();
    }

    public Map<String, Object> getPaginatedBranches(int page, int size) {
        int limit = size;
        int offset = page * size;

        List<Branch> branches = repository.findAll(limit, offset);
        long totalElements = repository.count();

        Map<String, Object> response = new HashMap<>();
        response.put("content", branches);
        response.put("totalElements", totalElements);

        return response;
    }

    public Branch getBranchById(Long orgcode, Long brncd) {
        return repository.findById(orgcode, brncd);
    }

    public void createBranch(Branch branch) {
        repository.save(branch);
    }

    public void updateBranch(Branch branch) {
        repository.update(branch);
    }

    public void deleteBranch(Long orgcode, Long brncd) {
        repository.delete(orgcode, brncd);
    }
}
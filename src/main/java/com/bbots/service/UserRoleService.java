package com.bbots.service;

import com.bbots.model.UserRoleAssign;
import com.bbots.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRoleService {

    @Autowired
    private UserRoleRepository repository;

    public List<UserRoleAssign> getAllAssignments() {
        return repository.findAll();
    }

    public List<UserRoleAssign> getAssignmentsByUserId(String userScd) {
        return repository.findByUserId(userScd);
    }

    public void assignRole(UserRoleAssign ura) {
        repository.save(ura);
    }

    public void revokeRole(String userScd, Integer roleCd) {
        repository.delete(userScd, roleCd);
    }
}

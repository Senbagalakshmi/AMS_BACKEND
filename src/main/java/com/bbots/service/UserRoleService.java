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
        List<UserRoleAssign> list = repository.findAll();
        System.out.println("DEBUG: getAllAssignments found " + (list != null ? list.size() : "null") + " records.");
        return list;
    }

    public List<UserRoleAssign> getAssignmentsByUserId(String userscd) {
        return repository.findByUserId(userscd);
    }

    public void assignRole(UserRoleAssign ura) {
        repository.save(ura);
    }

    public void revokeRole(String usersCd, Integer roleCd) {
        repository.delete(usersCd, roleCd);
    }
}

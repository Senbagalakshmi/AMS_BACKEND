package com.bbots.service;

import com.bbots.model.Role;
import com.bbots.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    @Autowired
    private RoleRepository repository;

    public List<Role> getAllRoles() {
        return repository.findAll();
    }

    public Role getRoleById(Integer roleCd) {
        return repository.findById(roleCd);
    }

    public void createRole(Role role) {
        repository.save(role);
    }

    public void updateRole(Role role) {
        repository.update(role);
    }

    public void deleteRole(Integer roleCd) {
        repository.delete(roleCd);
    }
}

package com.bbots.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bbots.model.Auth101Config;
import com.bbots.model.AuthConfigDTO;
import com.bbots.model.AuthRecord;
import com.bbots.repository.AuthRepository;

import java.util.List;

@Service
public class AuthService {

    @Autowired
    private AuthRepository repository;

    public List<Auth101Config> getAllConfigs() {
        return repository.getAllConfigs();
    }

    public Auth101Config updateConfig(Auth101Config cfg) {
        repository.updateConfig(cfg);
        return repository.getConfigById(cfg.getId());
    }

    public List<AuthConfigDTO> getAllAuthConfigs() {
        return repository.getAllAuthConfigs();
    }

    public void createAuthConfig(AuthConfigDTO dto) {
        repository.createAuthConfig(dto);
    }

    public List<AuthRecord> getAuthQueue() {
        return repository.getQueue();
    }

    public void approve(Long authSl, int level, String userId) {
        repository.processAuth(authSl, level, userId, 1);
    }

    public void reject(Long authSl, int level, String userId) {
        repository.processAuth(authSl, level, userId, 2);
    }
}

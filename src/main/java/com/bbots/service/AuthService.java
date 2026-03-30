package com.bbots.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bbots.model.Auth101Config;
import com.bbots.model.AuthConfigDTO;
import com.bbots.model.AuthRecord;
import com.bbots.repository.AuthRepository;
import com.bbots.repository.ModuleRepository;
import com.bbots.model.AuthDataBlock;
import com.bbots.model.Module;
import com.bbots.model.SubModule;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

@Service
public class AuthService {

    @Autowired
    private AuthRepository repository;

    @Autowired
    private ModuleRepository moduleRepository;

    @Autowired
    private ObjectMapper objectMapper;

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

    public List<AuthRecord> getMyRequests(String userId) {
        return repository.getQueueByUser(userId);
    }

    public void approve(Long authSl, int level, String userId) {
        // 1. Get metadata and data before approval processing (since the procedure may delete it from queue)
        String programId = repository.getProgramId(authSl);
        List<AuthDataBlock> blocks = repository.getDataBlocks(authSl);
        
        System.out.println("Processing Approval for Program: [" + programId + "] AuthSl: " + authSl);
        
        // 2. Standard Approval Process (Procedure Call) - Status 1
        repository.processAuth(authSl, level, userId, 1, null);
        
        // 3. Post-Approval Custom Logic for Module Creation
        if (programId != null && "MOD-CRT".equals(programId.trim())) {
            // ... (rest of module sync logic stays clean) ...
            try {
                if (blocks != null) {
                    for (AuthDataBlock block : blocks) {
                        Module m = objectMapper.readValue(block.getDataBlock(), Module.class);
                        if (m.getSubModuleRequired() != null && m.getSubModuleRequired() == 1) {
                            SubModule sm = new SubModule();
                            sm.setOrgcode(m.getOrgcode());
                            sm.setModuleId(m.getModuleId());
                            sm.setSubModuleId(m.getSubModuleId());
                            sm.setSubModuleName(m.getSubModuleName());
                            sm.setStatus(1);
                            sm.setEUser(m.getEUser());
                            moduleRepository.saveSubModule(sm);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void reject(Long authSl, int level, String userId) {
        // Status 0 for Reject
        repository.processAuth(authSl, level, userId, 0, null);
    }

    public void correction(Long authSl, int level, String userId, String remarks) {
        // Status 2 for Correction Requested
        repository.processAuth(authSl, level, userId, 2, remarks);
    }
}

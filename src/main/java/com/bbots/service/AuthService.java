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

    public void approve(Long authSl, int level, String userId) {
        // 1. Get metadata and data before approval processing (since the procedure may delete it from queue)
        String programId = repository.getProgramId(authSl);
        List<AuthDataBlock> blocks = repository.getDataBlocks(authSl);
        
        System.out.println("Processing Approval for Program: [" + programId + "] AuthSl: " + authSl);
        
        // 2. Original Approval Process (Procedure Call)
        repository.processAuth(authSl, level, userId, 1);
        
        // 3. Post-Approval Custom Logic for Module Creation
        if (programId != null && "MOD-CRT".equals(programId.trim())) {
            try {
                System.out.println("Executing Post-Approval Sub-Module Sync. Blocks found: " + (blocks != null ? blocks.size() : 0));
                if (blocks != null) {
                    for (AuthDataBlock block : blocks) {
                        System.out.println("Processing DataBlock: " + block.getDataBlock());
                        // Parse the JSON data block
                        Module m = objectMapper.readValue(block.getDataBlock(), Module.class);
                        
                        System.out.println("Parsed Module: ID=" + m.getModuleId() + ", SubModuleReq=" + m.getSubModuleRequired());
                        
                        // If sub-module is required and details are provided
                        if (m.getSubModuleRequired() != null && m.getSubModuleRequired() == 1) {
                            SubModule sm = new SubModule();
                            sm.setOrgcode(m.getOrgcode());
                            sm.setModuleId(m.getModuleId());
                            sm.setSubModuleId(m.getSubModuleId());
                            sm.setSubModuleName(m.getSubModuleName());
                            sm.setStatus(1); // Enable by default
                            sm.setEUser(m.getEUser());
                            
                            System.out.println("Attempting to save SubModule: " + sm.getSubModuleId() + " - " + sm.getSubModuleName());
                            // Save to Module002
                            moduleRepository.saveSubModule(sm);
                            System.out.println("✅ Auto-created Sub-Module for Module: " + m.getModuleId());
                        } else {
                            System.out.println("ℹ️ Sub-Module NOT required for this module creation.");
                        }
                    }
                }
            } catch (Exception e) {
                System.err.println("❌ Error in Post-Approval Sub-Module creation: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public void reject(Long authSl, int level, String userId) {
        repository.processAuth(authSl, level, userId, 2);
    }
}

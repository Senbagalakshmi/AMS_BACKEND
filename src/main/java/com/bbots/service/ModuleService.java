package com.bbots.service;

import com.bbots.model.Module;
import com.bbots.model.SubModule;
import com.bbots.repository.ModuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ModuleService {

    @Autowired
    private ModuleRepository repository;

    // Module logic
    public List<Module> getAllModules() {
        return repository.findAllModules();
    }

    public void createModule(Module m) {
        repository.saveModule(m);
    }

    public void updateModule(Module m) {
        repository.updateModule(m);
    }

    public void deleteModule(Integer id) {
        repository.deleteModule(id);
    }

    // Sub-Module logic
    public List<SubModule> getSubsByModuleId(Integer moduleId) {
        return repository.findSubsByModuleId(moduleId);
    }

    public void createSubModule(SubModule sm) {
        repository.saveSubModule(sm);
    }

    public void updateSubModule(SubModule sm) {
        repository.updateSubModule(sm);
    }

    public void deleteSubModule(Integer moduleId, Integer subModuleId) {
        repository.deleteSubModule(moduleId, subModuleId);
    }
}

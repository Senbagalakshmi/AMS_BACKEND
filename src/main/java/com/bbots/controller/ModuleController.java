package com.bbots.controller;

import com.bbots.model.Module;
import com.bbots.model.SubModule;
import com.bbots.service.ModuleService;
import com.bbots.service.AuthorizationProcedureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/modules")
@CrossOrigin(origins = "*")
public class ModuleController {

    @Autowired
    private ModuleService service;

    @Autowired
    private AuthorizationProcedureService authProcedureService;

    // --- Modules ---
    @GetMapping
    public List<Module> getModules() {
        return service.getAllModules();
    }

    @PostMapping
    public void createModule(@RequestBody Module m) {
        if (m.getOrgcode() == null) m.setOrgcode(50L);
        authProcedureService.processAuthorization(m.getOrgcode(), "MOD-CRT", "MODULE001", m);
    }

    @PutMapping
    public void updateModule(@RequestBody Module m) {
        service.updateModule(m);
    }

    @DeleteMapping("/{id}")
    public void deleteModule(@PathVariable Integer id) {
        service.deleteModule(id);
    }

    // --- Sub-Modules ---
    @GetMapping("/{moduleId}/subs")
    public List<SubModule> getSubs(@PathVariable Integer moduleId) {
        return service.getSubsByModuleId(moduleId);
    }

    @PostMapping("/{moduleId}/subs")
    public void createSub(@PathVariable Integer moduleId, @RequestBody SubModule sm) {
        sm.setModuleId(moduleId);
        if (sm.getOrgcode() == null) sm.setOrgcode(50L);
        authProcedureService.processAuthorization(sm.getOrgcode(), "MOD-CRT", "MODULE002", sm);
    }

    @PutMapping("/{moduleId}/subs")
    public void updateSub(@PathVariable Integer moduleId, @RequestBody SubModule sm) {
        sm.setModuleId(moduleId);
        service.updateSubModule(sm);
    }

    @DeleteMapping("/{moduleId}/subs/{subId}")
    public void deleteSub(@PathVariable Integer moduleId, @PathVariable Integer subId) {
        service.deleteSubModule(moduleId, subId);
    }
}

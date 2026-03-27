package com.bbots.controller;

import com.bbots.model.Menu001;
import com.bbots.model.Menu002;
import com.bbots.model.Menu003;
import com.bbots.model.Menu004;
import com.bbots.service.MenuService;
import com.bbots.service.AuthorizationProcedureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/menus")
@CrossOrigin(origins = "*")
public class MenuController {

    @Autowired
    private MenuService service;

    @Autowired
    private AuthorizationProcedureService authProcedureService;

    // --- Programs (MENU001) ---
    @GetMapping("/programs")
    public List<Menu001> getPrograms() {
        return service.getAllPrograms();
    }

    @PostMapping("/programs")
    public void createProgram(@RequestBody Menu001 m) {
        if (m.getOrgCode() == null) {
            m.setOrgCode(50L);
        }
        authProcedureService.processAuthorization(m.getOrgCode(), "MENU-CRT", "MENU001", m);
    }

    // --- Hierarchical Menu Fetch ---
    @GetMapping("/parent/{roleCd}")
    public List<Menu002> getParents(@PathVariable Integer roleCd) {
        return service.getParentMenus(roleCd);
    }

    @GetMapping("/sub/{roleCd}/{menuCode}")
    public List<Menu003> getSubs(@PathVariable Integer roleCd, @PathVariable Integer menuCode) {
        return service.getSubMenus(roleCd, menuCode);
    }

    @GetMapping("/links/{roleCd}/{menuCode}/{subCode}")
    public List<Menu004> getLinks(@PathVariable Integer roleCd, @PathVariable Integer menuCode,
            @PathVariable Integer subCode) {
        return service.getMenuLinks(roleCd, menuCode, subCode);
    }

    // --- Creation Endpoints ---
    @PostMapping("/parent")
    public void addParent(@RequestBody Menu002 m) {
        if (m.getOrgCode() == null) {
            m.setOrgCode(50L);
        }
        authProcedureService.processAuthorization(m.getOrgCode(), "MENU-CRT", "MENU002", m);
    }

    @PostMapping("/sub")
    public void addSub(@RequestBody Menu003 m) {
        if (m.getOrgCode() == null) {
            m.setOrgCode(50L);
        }
        authProcedureService.processAuthorization(m.getOrgCode(), "MENU-CRT", "MENU003", m);
    }

    @PostMapping("/link")
    public void addLink(@RequestBody Menu004 m) {
        if (m.getOrgCode() == null) {
            m.setOrgCode(50L);
        }
        authProcedureService.processAuthorization(m.getOrgCode(), "MENU-CRT", "MENU004", m);
    }
}

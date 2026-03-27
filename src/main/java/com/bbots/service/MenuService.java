package com.bbots.service;

import com.bbots.model.Menu001;
import com.bbots.model.Menu002;
import com.bbots.model.Menu003;
import com.bbots.model.Menu004;
import com.bbots.repository.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuService {

    @Autowired
    private MenuRepository repository;

    // Programs
    public List<Menu001> getAllPrograms() {
        return repository.getAllPrograms();
    }
    public void createProgram(Menu001 m) {
        repository.saveProgram(m);
    }

    // Hierarchical fetch
    public List<Menu002> getParentMenus(Integer roleCd) {
        return repository.getParentMenusByRole(roleCd);
    }

    public List<Menu003> getSubMenus(Integer roleCd, Integer menuCode) {
        return repository.getSubMenus(roleCd, menuCode);
    }

    public List<Menu004> getMenuLinks(Integer roleCd, Integer menuCode, Integer subMenuCode) {
        return repository.getMenuPrograms(roleCd, menuCode, subMenuCode);
    }

    // Creation
    public void createParentMenu(Menu002 m) {
        repository.saveParentMenu(m);
    }
    public void createSubMenu(Menu003 m) {
        repository.saveSubMenu(m);
    }
    public void createMenuLink(Menu004 m) {
        repository.saveMenuProgram(m);
    }
}

package com.bbots.repository;

import com.bbots.model.Menu001;
import com.bbots.model.Menu002;
import com.bbots.model.Menu003;
import com.bbots.model.Menu004;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MenuRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // --- Mappers ---
    private final RowMapper<Menu001> mapper001 = (rs, rowNum) -> new Menu001(
            rs.getLong("ORGCODE"), rs.getString("PGM_ID"), rs.getString("DESCN"), rs.getInt("MODULE"),
            rs.getInt("SUB_MODULE"), rs.getInt("PGM_CLASS"), rs.getInt("status"),
            rs.getString("REMARKS"), rs.getTimestamp("EDATE"), rs.getString("EUSER")
    );

    private final RowMapper<Menu002> mapper002 = (rs, rowNum) -> new Menu002(
            rs.getLong("ORGCODE"), rs.getInt("ROLECD"), rs.getInt("Menucode"), rs.getString("MENU_DESCN"),
            rs.getInt("MENU_ORDER"), rs.getInt("SUBMENUREQ"), rs.getString("PARENTMENU_PGMID"),
            rs.getString("Program Path"), rs.getString("MENU_LOGO"), rs.getString("MENU_LOCATION"),
            rs.getInt("MENU_STATUS"), rs.getTimestamp("EDATE"), rs.getString("EUSER")
    );

    private final RowMapper<Menu003> mapper003 = (rs, rowNum) -> new Menu003(
            rs.getLong("ORGCODE"), rs.getInt("ROLECD"), rs.getInt("Menucode"), rs.getInt("submenucode"),
            rs.getString("Description"), rs.getInt("MENU_ORDER"), rs.getString("SUBMENU_PGMID"),
            rs.getString("Program Path"), rs.getString("MENU_LOGO"), rs.getInt("MENU_STATUS"),
            rs.getTimestamp("EDATE"), rs.getString("EUSER")
    );

    private final RowMapper<Menu004> mapper004 = (rs, rowNum) -> new Menu004(
            rs.getLong("ORGCODE"), rs.getInt("ROLECD"), rs.getInt("Menucode"), rs.getInt("submenucode"),
            rs.getString("PGM_ID"), rs.getString("Decription"), rs.getInt("MENU_ORDER"),
            rs.getString("Program Path"), rs.getString("MENU_LOGO"), rs.getInt("status"),
            rs.getTimestamp("EDATE"), rs.getString("EUSER")
    );

    // --- MENU001 Methods ---
    public List<Menu001> getAllPrograms() {
        return jdbcTemplate.query("SELECT * FROM MENU001", mapper001);
    }
    public void saveProgram(Menu001 m) {
        String sql = "INSERT INTO MENU001 (ORGCODE, PGM_ID, DESCN, MODULE, SUB_MODULE, PGM_CLASS, status, REMARKS, EUSER) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, m.getOrgCode(), m.getPgmId(), m.getDescn(), m.getModule(), m.getSubModule(), m.getPgmClass(), m.getStatus(), m.getRemarks(), m.getEUser());
    }

    // --- MENU002 Methods ---
    public List<Menu002> getParentMenusByRole(Integer roleCd) {
        return jdbcTemplate.query("SELECT * FROM MENU002 WHERE ROLECD = ? ORDER BY MENU_ORDER", mapper002, roleCd);
    }
    public void saveParentMenu(Menu002 m) {
        String sql = "INSERT INTO MENU002 (ORGCODE, ROLECD, Menucode, MENU_DESCN, MENU_ORDER, SUBMENUREQ, PARENTMENU_PGMID, \"Program Path\", MENU_LOGO, MENU_LOCATION, MENU_STATUS, EUSER) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, m.getOrgCode(), m.getRoleCd(), m.getMenuCode(), m.getMenuDescn(), m.getMenuOrder(), m.getSubMenuReq(), m.getParentMenuPgmId(), m.getProgramPath(), m.getMenuLogo(), m.getMenuLocation(), m.getMenuStatus(), m.getEUser());
    }

    // --- MENU003 Methods ---
    public List<Menu003> getSubMenus(Integer roleCd, Integer menuCode) {
        return jdbcTemplate.query("SELECT * FROM MENU003 WHERE ROLECD = ? AND Menucode = ? ORDER BY MENU_ORDER", mapper003, roleCd, menuCode);
    }
    public void saveSubMenu(Menu003 m) {
        String sql = "INSERT INTO MENU003 (ORGCODE, ROLECD, Menucode, submenucode, Description, MENU_ORDER, SUBMENU_PGMID, \"Program Path\", MENU_LOGO, MENU_STATUS, EUSER) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, m.getOrgCode(), m.getRoleCd(), m.getMenuCode(), m.getSubMenuCode(), m.getDescription(), m.getMenuOrder(), m.getSubMenuPgmId(), m.getProgramPath(), m.getMenuLogo(), m.getMenuStatus(), m.getEUser());
    }

    // --- MENU004 Methods ---
    public List<Menu004> getMenuPrograms(Integer roleCd, Integer menuCode, Integer subMenuCode) {
        return jdbcTemplate.query("SELECT * FROM MENU004 WHERE ROLECD = ? AND Menucode = ? AND submenucode = ? ORDER BY MENU_ORDER", mapper004, roleCd, menuCode, subMenuCode);
    }
    public void saveMenuProgram(Menu004 m) {
        String sql = "INSERT INTO MENU004 (ORGCODE, ROLECD, Menucode, submenucode, PGM_ID, Decription, MENU_ORDER, \"Program Path\", MENU_LOGO, status, EUSER) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, m.getOrgCode(), m.getRoleCd(), m.getMenuCode(), m.getSubMenuCode(), m.getPgmId(), m.getDescription(), m.getMenuOrder(), m.getProgramPath(), m.getMenuLogo(), m.getStatus(), m.getEUser());
    }
}

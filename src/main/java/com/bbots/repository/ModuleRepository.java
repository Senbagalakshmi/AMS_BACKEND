package com.bbots.repository;

import com.bbots.model.Module;
import com.bbots.model.SubModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ModuleRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<Module> moduleMapper = (rs, rowNum) -> new Module(
            rs.getInt("MODULE_ID"),
            rs.getString("MODULENAME"),
            rs.getInt("SUB_MODULE"),
            rs.getInt("status"),
            rs.getTimestamp("EDATE"),
            rs.getString("EUSER"),
            rs.getTimestamp("CDATE"),
            rs.getString("CUSER"),
            rs.getTimestamp("ADATE"),
            rs.getString("AUSER")
    );

    private final RowMapper<SubModule> subModuleMapper = (rs, rowNum) -> new SubModule(
            rs.getInt("MODULE_ID"),
            rs.getInt("SUB_MODULEID"),
            rs.getString("SUB_MODULENAME"),
            rs.getInt("status"),
            rs.getTimestamp("EDATE"),
            rs.getString("EUSER"),
            rs.getTimestamp("CDATE"),
            rs.getString("CUSER"),
            rs.getTimestamp("ADATE"),
            rs.getString("AUSER")
    );

    // --- Module Methods ---
    public List<Module> findAllModules() {
        return jdbcTemplate.query("SELECT * FROM MODULE001", moduleMapper);
    }

    public Module findModuleById(Integer id) {
        return jdbcTemplate.queryForObject("SELECT * FROM MODULE001 WHERE MODULE_ID = ?", moduleMapper, id);
    }

    public void saveModule(Module m) {
        String sql = "INSERT INTO MODULE001 (MODULE_ID, MODULENAME, SUB_MODULE, status, EUSER) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, m.getModuleId(), m.getModuleName(), m.getSubModuleRequired(), m.getStatus(), m.getEUser());
    }

    public void updateModule(Module m) {
        String sql = "UPDATE MODULE001 SET MODULENAME=?, SUB_MODULE=?, status=?, CUSER=?, CDATE=CURRENT_TIMESTAMP WHERE MODULE_ID=?";
        jdbcTemplate.update(sql, m.getModuleName(), m.getSubModuleRequired(), m.getStatus(), m.getCUser(), m.getModuleId());
    }

    public void deleteModule(Integer id) {
        jdbcTemplate.update("DELETE FROM MODULE001 WHERE MODULE_ID = ?", id);
    }

    // --- Sub-Module Methods ---
    public List<SubModule> findSubsByModuleId(Integer moduleId) {
        return jdbcTemplate.query("SELECT * FROM MODULE002 WHERE MODULE_ID = ?", subModuleMapper, moduleId);
    }

    public void saveSubModule(SubModule sm) {
        String sql = "INSERT INTO MODULE002 (MODULE_ID, SUB_MODULEID, SUB_MODULENAME, status, EUSER) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, sm.getModuleId(), sm.getSubModuleId(), sm.getSubModuleName(), sm.getStatus(), sm.getEUser());
    }

    public void updateSubModule(SubModule sm) {
        String sql = "UPDATE MODULE002 SET SUB_MODULENAME=?, status=?, CUSER=?, CDATE=CURRENT_TIMESTAMP WHERE MODULE_ID=? AND SUB_MODULEID=?";
        jdbcTemplate.update(sql, sm.getSubModuleName(), sm.getStatus(), sm.getCUser(), sm.getModuleId(), sm.getSubModuleId());
    }

    public void deleteSubModule(Integer moduleId, Integer subModuleId) {
        jdbcTemplate.update("DELETE FROM MODULE002 WHERE MODULE_ID = ? AND SUB_MODULEID = ?", moduleId, subModuleId);
    }
}

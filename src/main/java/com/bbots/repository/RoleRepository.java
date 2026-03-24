package com.bbots.repository;

import com.bbots.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RoleRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<Role> roleMapper = (rs, rowNum) -> new Role(
            rs.getInt("ORGCODE"),
            rs.getInt("ROLECD"),
            rs.getString("ROLENAME"),
            rs.getString("ROLETYPE"),
            rs.getString("ROLESUBTYPE"),
            rs.getInt("VIEWACCESS"),
            rs.getInt("AUTHACCESS"),
            rs.getInt("MAKERACCESS"),
            rs.getInt("ADMINACCESS"),
            rs.getInt("SYSADMINACCESS"),
            rs.getString("EUSER"),
            rs.getTimestamp("EDATE"),
            rs.getString("AUSER"),
            rs.getTimestamp("ADATE"),
            rs.getString("CUSER"),
            rs.getTimestamp("CDATE")
    );

    public List<Role> findAll() {
        return jdbcTemplate.query("SELECT * FROM ROLE001", roleMapper);
    }

    public Role findById(Integer roleCd) {
        return jdbcTemplate.queryForObject("SELECT * FROM ROLE001 WHERE ROLECD = ?", roleMapper, roleCd);
    }

    public void save(Role role) {
        String sql = "INSERT INTO ROLE001 (ORGCODE, ROLECD, ROLENAME, ROLETYPE, ROLESUBTYPE, VIEWACCESS, AUTHACCESS, MAKERACCESS, ADMINACCESS, SYSADMINACCESS, EUSER) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, role.getOrgCode(), role.getRoleCd(), role.getRoleName(), role.getRoleType(),
                role.getRoleSubtype(), role.getViewAccess(), role.getAuthAccess(), role.getMakerAccess(),
                role.getAdminAccess(), role.getSysAdminAccess(), role.getEUser());
    }

    public void update(Role role) {
        String sql = "UPDATE ROLE001 SET ORGCODE=?, ROLENAME=?, ROLETYPE=?, ROLESUBTYPE=?, VIEWACCESS=?, AUTHACCESS=?, MAKERACCESS=?, ADMINACCESS=?, SYSADMINACCESS=?, CUSER=?, CDATE=CURRENT_TIMESTAMP " +
                     "WHERE ROLECD=?";
        jdbcTemplate.update(sql, role.getOrgCode(), role.getRoleName(), role.getRoleType(), role.getRoleSubtype(),
                role.getViewAccess(), role.getAuthAccess(), role.getMakerAccess(), role.getAdminAccess(),
                role.getSysAdminAccess(), role.getCUser(), role.getRoleCd());
    }

    public void delete(Integer roleCd) {
        jdbcTemplate.update("DELETE FROM ROLE001 WHERE ROLECD = ?", roleCd);
    }
}

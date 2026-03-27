package com.bbots.repository;

import com.bbots.model.UserRoleAssign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRoleRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<UserRoleAssign> userRoleMapper = (rs, rowNum) -> {
        UserRoleAssign ura = new UserRoleAssign();
        ura.setOrgcode(rs.getLong("ORGCODE"));
        ura.setUserscd(rs.getString("USERSCD"));
        ura.setRolecd(rs.getInt("ROLECD"));
        
        // Optional/Joined fields
        try { ura.setRoleName(rs.getString("ROLENAME")); } catch (Exception e) {}
        try { ura.setEuser(rs.getString("EUSER")); } catch (Exception e) {}
        try { ura.setEdate(rs.getTimestamp("EDATE")); } catch (Exception e) {}
        try { ura.setAUser(rs.getString("AUSER")); } catch (Exception e) {}
        try { ura.setADate(rs.getTimestamp("ADATE")); } catch (Exception e) {}
        try { ura.setCUser(rs.getString("CUSER")); } catch (Exception e) {}
        try { ura.setCDate(rs.getTimestamp("CDATE")); } catch (Exception e) {}
        
        return ura;
    };

    public List<UserRoleAssign> findAll() {
        String sql = "SELECT t1.*, t2.ROLENAME FROM USERS002 t1 LEFT JOIN ROLE001 t2 ON t1.ROLECD = t2.ROLECD AND t1.ORGCODE = t2.ORGCODE";
        return jdbcTemplate.query(sql, userRoleMapper);
    }

    public List<UserRoleAssign> findByUserId(String userscd) {
        String sql = "SELECT t1.*, t2.ROLENAME FROM USERS002 t1 LEFT JOIN ROLE001 t2 ON t1.ROLECD = t2.ROLECD AND t1.ORGCODE = t2.ORGCODE WHERE t1.USERSCD = ?";
        return jdbcTemplate.query(sql, userRoleMapper, userscd);
    }

    public void save(UserRoleAssign ura) {
        String sql = "INSERT INTO USERS002 (ORGCODE, USERSCD, ROLECD, EUSER) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, ura.getOrgcode(), ura.getUserscd(), ura.getRolecd(), ura.getEuser());
    }

    public void delete(String usersCd, Integer roleCd) {
        jdbcTemplate.update("DELETE FROM USERS002 WHERE USERSCD = ? AND ROLECD = ?", usersCd, roleCd);
    }
}

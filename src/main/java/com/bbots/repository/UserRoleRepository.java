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

    private final RowMapper<UserRoleAssign> userRoleMapper = (rs, rowNum) -> new UserRoleAssign(
            rs.getInt("ORGCODE"),
            rs.getString("USERSCD"),
            rs.getInt("ROLECD"),
            rs.getString("EUSER"),
            rs.getTimestamp("EDATE"),
            rs.getString("AUSER"),
            rs.getTimestamp("ADATE"),
            rs.getString("CUSER"),
            rs.getTimestamp("CDATE")
    );

    public List<UserRoleAssign> findAll() {
        return jdbcTemplate.query("SELECT * FROM USERS002", userRoleMapper);
    }

    public List<UserRoleAssign> findByUserId(String userScd) {
        return jdbcTemplate.query("SELECT * FROM USERS002 WHERE USERSCD = ?", userRoleMapper, userScd);
    }

    public void save(UserRoleAssign ura) {
        String sql = "INSERT INTO USERS002 (ORGCODE, USERSCD, ROLECD, EUSER) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, ura.getOrgCode(), ura.getUserScd(), ura.getRoleCd(), ura.getEUser());
    }

    public void delete(String userScd, Integer roleCd) {
        jdbcTemplate.update("DELETE FROM USERS002 WHERE USERSCD = ? AND ROLECD = ?", userScd, roleCd);
    }
}

package com.bbots.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.bbots.model.User;

import java.util.List;

@Repository
public class UserRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<User> userMapper = (rs, rowNum) -> new User(
            rs.getLong("ORGCODE"),
            rs.getString("USERSCD"),
            rs.getString("TITLE"),
            rs.getString("FNAME"),
            rs.getString("MNAME"),
            rs.getString("LNAME"),
            rs.getDate("REGDATE"),
            rs.getInt("STATUS"),
            rs.getString("BRANCHCD"),
            rs.getString("PICTURE"),
            rs.getString("GENDER"),
            rs.getDate("DOB"),
            rs.getString("EMAILID"),
            rs.getLong("CALLCODE"),
            rs.getInt("COUNTRY"),
            rs.getString("MOBILE"),
            rs.getString("PASSWORD_HASH"),
            rs.getString("PASSWORD_SALT"),
            rs.getString("EUSER"),
            rs.getTimestamp("EDATE"),
            rs.getString("AUSER"),
            rs.getTimestamp("ADATE"),
            rs.getString("CUSER"),
            rs.getTimestamp("CDATE")
    );

    public List<User> findAll() {
        String sql = "SELECT u1.*, u2.emailid, u2.callcode, u2.country, u2.mobile, u3.PASSWORD_HASH, u3.PASSWORD_SALT " +
                     "FROM USER001 u1 " +
                     "LEFT JOIN USER002 u2 ON u1.USERSCD = u2.USERSCD AND u1.ORGCODE = u2.ORGCODE " +
                     "LEFT JOIN USER003 u3 ON u1.USERSCD = u3.USERSCD AND u1.ORGCODE = u3.ORGCODE";
        return jdbcTemplate.query(sql, userMapper);
    }

    public List<User> findAll(int limit, int offset) {
        String sql = "SELECT u1.*, u2.emailid, u2.callcode, u2.country, u2.mobile, u3.PASSWORD_HASH, u3.PASSWORD_SALT " +
                     "FROM USER001 u1 " +
                     "LEFT JOIN USER002 u2 ON u1.USERSCD = u2.USERSCD AND u1.ORGCODE = u2.ORGCODE " +
                     "LEFT JOIN USER003 u3 ON u1.USERSCD = u3.USERSCD AND u1.ORGCODE = u3.ORGCODE " +
                     "LIMIT ? OFFSET ?";
        return jdbcTemplate.query(sql, userMapper, limit, offset);
    }

    public long count() {
        return jdbcTemplate.queryForObject("SELECT count(*) FROM USER001", Long.class);
    }

    public User findById(Long orgcode, String userscd) {
        String sql = "SELECT u1.*, u2.emailid, u2.callcode, u2.country, u2.mobile, u3.PASSWORD_HASH, u3.PASSWORD_SALT " +
                     "FROM USER001 u1 " +
                     "LEFT JOIN USER002 u2 ON u1.USERSCD = u2.USERSCD AND u1.ORGCODE = u2.ORGCODE " +
                     "LEFT JOIN USER003 u3 ON u1.USERSCD = u3.USERSCD AND u1.ORGCODE = u3.ORGCODE " +
                     "WHERE u1.ORGCODE = ? AND u1.USERSCD = ?";
        return jdbcTemplate.queryForObject(sql, userMapper, orgcode, userscd);
    }

    public void save(User user) {
        // Insert into USER001
        String sql1 = "INSERT INTO USER001 (ORGCODE, USERSCD, TITLE, FNAME, MNAME, LNAME, REGDATE, STATUS, BRANCHCD, PICTURE, GENDER, DOB, EUSER, EDATE) " +
                      "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql1, user.getOrgcode(), user.getUserscd(), user.getTitle(), user.getFname(), user.getMname(), user.getLname(),
                user.getRegdate(), user.getStatus(), user.getBranchcd(), user.getPicture(), user.getGender(), user.getDob(),
                user.getEuser(), user.getEdate());

        // Insert into USER002
        String sql2 = "INSERT INTO USER002 (emailid, USERSCD, callcode, country, mobile, ORGCODE) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql2, user.getEmailid(), user.getUserscd(), user.getCallcode(), user.getCountry(), user.getMobile(), user.getOrgcode());

        // Insert into USER003
        String sql3 = "INSERT INTO USER003 (ORGCODE, USERSCD, PASSWORD_HASH, PASSWORD_SALT, EUSER, EDATE) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql3, user.getOrgcode(), user.getUserscd(), user.getPasswordHash(), user.getPasswordSalt(), user.getEuser(), user.getEdate());
    }

    public void update(User user) {
        // Update USER001
        String sql1 = "UPDATE USER001 SET TITLE=?, FNAME=?, MNAME=?, LNAME=?, REGDATE=?, STATUS=?, BRANCHCD=?, PICTURE=?, GENDER=?, DOB=?, CUSER=?, CDATE=? " +
                      "WHERE ORGCODE=? AND USERSCD=?";
        jdbcTemplate.update(sql1, user.getTitle(), user.getFname(), user.getMname(), user.getLname(), user.getRegdate(),
                user.getStatus(), user.getBranchcd(), user.getPicture(), user.getGender(), user.getDob(),
                user.getCuser(), user.getCdate(), user.getOrgcode(), user.getUserscd());

        // Update USER002
        String sql2 = "UPDATE USER002 SET emailid=?, callcode=?, country=?, mobile=? WHERE ORGCODE=? AND USERSCD=?";
        jdbcTemplate.update(sql2, user.getEmailid(), user.getCallcode(), user.getCountry(), user.getMobile(), user.getOrgcode(), user.getUserscd());

        // Update USER003
        if (user.getPasswordHash() != null) {
            String sql3 = "UPDATE USER003 SET PASSWORD_HASH=?, PASSWORD_SALT=?, CUSER=?, CDATE=? WHERE ORGCODE=? AND USERSCD=?";
            jdbcTemplate.update(sql3, user.getPasswordHash(), user.getPasswordSalt(), user.getCuser(), user.getCdate(), user.getOrgcode(), user.getUserscd());
        }
    }

    public void delete(Long orgcode, String userscd) {
        jdbcTemplate.update("DELETE FROM USER003 WHERE ORGCODE = ? AND USERSCD = ?", orgcode, userscd);
        jdbcTemplate.update("DELETE FROM USER002 WHERE ORGCODE = ? AND USERSCD = ?", orgcode, userscd);
        jdbcTemplate.update("DELETE FROM USER001 WHERE ORGCODE = ? AND USERSCD = ?", orgcode, userscd);
    }
    public Object[] getUserProfileByUsername(String username) {
        String sql =
                "SELECT CONCAT(u1.FNAME,' ',u1.LNAME) username, " +
                "u2.emailid email, " +
                "r.ROLENAME role " +
                "FROM USER002 u2 " +
                "JOIN USER001 u1 ON u2.USERSCD = u1.USERSCD AND u2.ORGCODE = u1.ORGCODE " +
                "LEFT JOIN USERS002 ura ON u1.USERSCD = ura.USERSCD AND u1.ORGCODE = ura.ORGCODE " +
                "LEFT JOIN ROLE001 r ON ura.ROLECD = r.ROLECD AND ura.ORGCODE = r.ORGCODE " +
                "WHERE u2.emailid = ?";

        return jdbcTemplate.queryForObject(sql,
                (rs, rowNum) -> new Object[]{
                        rs.getString("username"),
                        rs.getString("email"),
                        rs.getString("role")
                }, username);
    }
    
}

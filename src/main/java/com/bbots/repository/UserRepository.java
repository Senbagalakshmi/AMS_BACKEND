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
            rs.getInt("ORGCODE"),
            rs.getString("USERSCD"),
            rs.getInt("MENUTYPE"),
            rs.getString("GENDER"),
            rs.getString("TITLE"),
            rs.getString("FNAME"),
            rs.getString("MNAME"),
            rs.getString("LNAME"),
            rs.getString("EMAIL"),
            rs.getString("MOBILE"),
            rs.getString("COUNTRY"),
            rs.getString("EUSER"),
            rs.getTimestamp("EDATE"),
            rs.getString("AUSER"),
            rs.getTimestamp("ADATE"),
            rs.getString("CUSER"),
            rs.getTimestamp("CDATE"),
            rs.getString("CLIENTCD")
    );

    public List<User> findAll() {
        return jdbcTemplate.query("SELECT * FROM USERS001", userMapper);
    }

    public User findById(String userscd) {
        return jdbcTemplate.queryForObject("SELECT * FROM USERS001 WHERE USERSCD = ?", userMapper, userscd);
    }

    public void save(User user) {
        String sql = "INSERT INTO USERS001 (ORGCODE, USERSCD, MENUTYPE, GENDER, TITLE, FNAME, MNAME, LNAME, EMAIL, MOBILE, COUNTRY, EUSER, CLIENTCD) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, user.getOrgCode(), user.getUserScd(), user.getMenuType(), user.getGender(),
                user.getTitle(), user.getFName(), user.getMName(), user.getLName(), user.getEmail(),
                user.getMobile(), user.getCountry(), user.getEUser(), user.getClientCd());
    }

    public void update(User user) {
        String sql = "UPDATE USERS001 SET ORGCODE=?, MENUTYPE=?, GENDER=?, TITLE=?, FNAME=?, MNAME=?, LNAME=?, EMAIL=?, MOBILE=?, COUNTRY=?, CLIENTCD=? " +
                     "WHERE USERSCD=?";
        jdbcTemplate.update(sql, user.getOrgCode(), user.getMenuType(), user.getGender(), user.getTitle(),
                user.getFName(), user.getMName(), user.getLName(), user.getEmail(), user.getMobile(),
                user.getCountry(), user.getClientCd(), user.getUserScd());
    }

    public void delete(String userscd) {
        jdbcTemplate.update("DELETE FROM USERS001 WHERE USERSCD = ?", userscd);
    }
    
    public Object[] getUserProfileByUsername(String username) {

        String sql =
                "SELECT CONCAT(u1.FNAME,' ',u1.LNAME) username," +
                "u1.EMAIL email," +
                "u2.ROLECD role " +
                "FROM USERS001 u1 " +
                "LEFT JOIN USERS002 u2 " +
                "ON u1.USERSCD = u2.USERSCD " +
                "WHERE u1.EMAIL = ?";

        return jdbcTemplate.queryForObject(sql,
                (rs, rowNum) -> new Object[]{
                        rs.getString("username"),
                        rs.getString("email"),
                        rs.getString("role")
                }, username);
    }
}

package com.bbots.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.bbots.model.Organisation;

import java.util.List;

@Repository
public class OrganisationRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<Organisation> organisationMapper = (rs, rowNum) -> new Organisation(
            rs.getLong("ORGCODE"),
            rs.getString("NAME"),
            rs.getDate("OPENDATE"),
            rs.getString("COUNTRY"),
            rs.getString("DIVISIONNAME"),
            rs.getString("PINCODE"),
            rs.getString("ADDRLINE1"),
            rs.getString("ADDRLINE2"),
            rs.getString("ADDRLINE3"),
            rs.getString("ADDRLINE4"),
            rs.getString("ADDRLINE5"),
            rs.getString("TELEPHONE"),
            rs.getString("EMAIL"),
            rs.getInt("STATUS"),
            rs.getInt("INDIV"),
            rs.getString("EUSER"),
            rs.getTimestamp("EDATE"),
            rs.getString("AUSER"),
            rs.getTimestamp("ADATE"),
            rs.getString("CUSER"),
            rs.getTimestamp("CDATE"),
            rs.getString("LOGO")
    );

    private static final String BASE_SELECT =
            "SELECT ORGCODE, NAME, OPENDATE, COUNTRY, DIVISIONNAME, PINCODE, " +
            "ADDRLINE1, ADDRLINE2, ADDRLINE3, ADDRLINE4, ADDRLINE5, " +
            "TELEPHONE, EMAIL, STATUS, INDIV, EUSER, EDATE, AUSER, ADATE, CUSER, CDATE, LOGO " +
            "FROM ORG001";

    public List<Organisation> findAll() {
        return jdbcTemplate.query(BASE_SELECT, organisationMapper);
    }

    public List<Organisation> findAll(int limit, int offset) {
        String sql = BASE_SELECT + " LIMIT ? OFFSET ?";
        return jdbcTemplate.query(sql, organisationMapper, limit, offset);
    }

    public long count() {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM ORG001", Long.class);
    }

    public Organisation findById(Long orgcode) {
        String sql = BASE_SELECT + " WHERE ORGCODE = ?";
        return jdbcTemplate.queryForObject(sql, organisationMapper, orgcode);
    }

    public void save(Organisation org) {
        String sql = "INSERT INTO ORG001 " +
                "(ORGCODE, NAME, OPENDATE, COUNTRY, DIVISIONNAME, PINCODE, " +
                "ADDRLINE1, ADDRLINE2, ADDRLINE3, ADDRLINE4, ADDRLINE5, " +
                "TELEPHONE, EMAIL, STATUS, INDIV, EUSER, EDATE, LOGO) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                org.getOrgcode(), org.getName(), org.getOpendate(), org.getCountry(),
                org.getDivisionname(), org.getPincode(), org.getAddrline1(), org.getAddrline2(),
                org.getAddrline3(), org.getAddrline4(), org.getAddrline5(), org.getTelephone(),
                org.getEmail(), org.getStatus(), org.getIndiv(), org.getEuser(), org.getEdate(),
                org.getLogo());
    }

    public void update(Organisation org) {
        String sql = "UPDATE ORG001 SET " +
                "NAME=?, OPENDATE=?, COUNTRY=?, DIVISIONNAME=?, PINCODE=?, " +
                "ADDRLINE1=?, ADDRLINE2=?, ADDRLINE3=?, ADDRLINE4=?, ADDRLINE5=?, " +
                "TELEPHONE=?, EMAIL=?, STATUS=?, INDIV=?, CUSER=?, CDATE=?, LOGO=? " +
                "WHERE ORGCODE=?";
        jdbcTemplate.update(sql,
                org.getName(), org.getOpendate(), org.getCountry(), org.getDivisionname(),
                org.getPincode(), org.getAddrline1(), org.getAddrline2(), org.getAddrline3(),
                org.getAddrline4(), org.getAddrline5(), org.getTelephone(), org.getEmail(),
                org.getStatus(), org.getIndiv(), org.getCuser(), org.getCdate(), org.getLogo(),
                org.getOrgcode());
    }

    public void delete(Long orgcode) {
        jdbcTemplate.update("DELETE FROM ORG001 WHERE ORGCODE = ?", orgcode);
    }
}

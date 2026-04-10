package com.bbots.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.bbots.model.Branch;

import java.util.List;

@Repository
public class BranchRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<Branch> branchMapper = (rs, rowNum) -> new Branch(
            rs.getLong("ORGCODE"),
            rs.getLong("BRNCD"),
            rs.getString("BRNNAME"),
            rs.getDate("OPENDATE"),
            rs.getString("ADDRESS"),
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
            rs.getString("EUSER"),
            rs.getTimestamp("EDATE"),
            rs.getString("AUSER"),
            rs.getTimestamp("ADATE"),
            rs.getString("CUSER"),
            rs.getTimestamp("CDATE"),
            rs.getInt("HEADBRN")
    );

    private static final String BASE_SELECT =
            "SELECT ORGCODE, BRNCD, BRNNAME, OPENDATE, ADDRESS, COUNTRY, " +
            "DIVISIONNAME, PINCODE, ADDRLINE1, ADDRLINE2, ADDRLINE3, ADDRLINE4, ADDRLINE5, " +
            "TELEPHONE, EMAIL, STATUS, EUSER, EDATE, AUSER, ADATE, CUSER, CDATE, HEADBRN " +
            "FROM BRANCH001";

    public List<Branch> findAll() {
        return jdbcTemplate.query(BASE_SELECT, branchMapper);
    }

    public List<Branch> findAll(int limit, int offset) {
        String sql = BASE_SELECT + " LIMIT ? OFFSET ?";
        return jdbcTemplate.query(sql, branchMapper, limit, offset);
    }

    public long count() {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM BRANCH001", Long.class);
    }

    public Branch findById(Long orgcode, Long brncd) {
        String sql = BASE_SELECT + " WHERE ORGCODE = ? AND BRNCD = ?";
        return jdbcTemplate.queryForObject(sql, branchMapper, orgcode, brncd);
    }

    public void save(Branch branch) {
        String sql = "INSERT INTO BRANCH001 " +
                "(ORGCODE, BRNCD, BRNNAME, OPENDATE, ADDRESS, COUNTRY, DIVISIONNAME, " +
                "PINCODE, ADDRLINE1, ADDRLINE2, ADDRLINE3, ADDRLINE4, ADDRLINE5, " +
                "TELEPHONE, EMAIL, STATUS, EUSER, EDATE, HEADBRN) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                branch.getOrgcode(), branch.getBrncd(), branch.getBrnname(),
                branch.getOpendate(), branch.getAddress(), branch.getCountry(), branch.getDivisionname(),
                branch.getPincode(), branch.getAddrline1(), branch.getAddrline2(), branch.getAddrline3(),
                branch.getAddrline4(), branch.getAddrline5(), branch.getTelephone(), branch.getEmail(),
                branch.getStatus(), branch.getEuser(), branch.getEdate(), branch.getHeadbrn());
    }

    public void update(Branch branch) {
        String sql = "UPDATE BRANCH001 SET " +
                "BRNNAME=?, OPENDATE=?, ADDRESS=?, COUNTRY=?, DIVISIONNAME=?, " +
                "PINCODE=?, ADDRLINE1=?, ADDRLINE2=?, ADDRLINE3=?, ADDRLINE4=?, ADDRLINE5=?, " +
                "TELEPHONE=?, EMAIL=?, STATUS=?, CUSER=?, CDATE=?, HEADBRN=? " +
                "WHERE ORGCODE=? AND BRNCD=?";
        jdbcTemplate.update(sql,
                branch.getBrnname(), branch.getOpendate(),
                branch.getAddress(), branch.getCountry(), branch.getDivisionname(),
                branch.getPincode(), branch.getAddrline1(), branch.getAddrline2(),
                branch.getAddrline3(), branch.getAddrline4(), branch.getAddrline5(),
                branch.getTelephone(), branch.getEmail(), branch.getStatus(),
                branch.getCuser(), branch.getCdate(), branch.getHeadbrn(),
                branch.getOrgcode(), branch.getBrncd());
    }

    public void delete(Long orgcode, Long brncd) {
        jdbcTemplate.update("DELETE FROM BRANCH001 WHERE ORGCODE = ? AND BRNCD = ?", orgcode, brncd);
    }
} 
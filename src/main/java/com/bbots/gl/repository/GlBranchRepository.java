package com.bbots.gl.repository;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.bbots.gl.model.GlBranch;

@Repository
public class GlBranchRepository {

    @Autowired
	private JdbcTemplate jdbcTemplate;

	
	
//	private final RowMapper<GlBranch> glBranch = (rs, rowNum) -> new GlBranch(
//			 rs.getLong("ORGCODE"),
//			 rs.getInt("GLNO"),
//			 rs.getString("ALLOWEDBRN"),
//			 rs.getString("EUSER"),
//			 rs.getTimestamp("EDATE"),
//			 rs.getString("AUSER"),
//			 rs.getTimestamp("ADATE"),
//			 rs.getString("CUSER"),
//			 rs.getTimestamp("CDATE")			          
//	    );
    private final RowMapper<GlBranch> glBranch = (rs, rowNum) -> new GlBranch(
    	     rs.getLong("ORGCODE"),
    	     rs.getInt("GLNO"),
    	     rs.getString("ALLOWEDBRN"),
    	     rs.getString("EUSER"),
    	     rs.getTimestamp("EDATE") != null ? new java.util.Date(rs.getTimestamp("EDATE").getTime()) : null,
    	     rs.getString("AUSER"),
    	     rs.getTimestamp("ADATE") != null ? new java.util.Date(rs.getTimestamp("ADATE").getTime()) : null,
    	     rs.getString("CUSER"),
    	     rs.getTimestamp("CDATE") != null ? new java.util.Date(rs.getTimestamp("CDATE").getTime()) : null
    	);

	public List<GlBranch> findAll() {
	    String sql = "SELECT ORGCODE, GLNO, ALLOWEDBRN, EUSER, EDATE, AUSER, ADATE, CUSER, CDATE FROM GL104";
	    return jdbcTemplate.query(sql, glBranch);
	}

	public List<GlBranch> findByUserId(Integer glNo) {
	    String sql = "SELECT ORGCODE, GLNO, ALLOWEDBRN, EUSER, EDATE, AUSER, ADATE, CUSER, CDATE FROM GL104 WHERE GLNO = ?";
	    return jdbcTemplate.query(sql, glBranch, glNo);
	}

	    public void save(GlBranch gb) {
	        String sql = "INSERT INTO GL104 (ORGCODE, GLNO, ALLOWEDBRN, EUSER, EDATE, AUSER, ADATE, CUSER, CDATE) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
	        jdbcTemplate.update(sql,
	        		gb.getOrgCode(),
	        		gb.getGlNo(),
	        		gb.getAllowedBrn(),
	        		gb.getEUser(),
	        		gb.getEDate(),
	        		gb.getAUser(),
	        		gb.getADate(),
	        		gb.getCUser(),
	        		gb.getCDate()
	        );
	    }

	    public void delete(Long orgCode, Integer glNo) {
	        String sql = "DELETE FROM GL104 WHERE ORGCODE = ? AND GLNO = ?";
	        jdbcTemplate.update(sql, orgCode, glNo);
	    }
	    
	    
	    public void update(GlBranch gb) {
          String sql = "UPDATE GL104 SET " +
            "ALLOWEDBRN = ?, " +                
            "EUSER = ?, " +
            "EDATE = ?, " +
            "AUSER = ?, " +
            "ADATE = ?, " +
            "CUSER = ?, " +
            "CDATE = ? " +
            "WHERE ORGCODE = ? AND GLNO = ?";

    jdbcTemplate.update(sql,
            gb.getAllowedBrn(),
            gb.getEUser(),
            gb.getEDate(),
            gb.getAUser(),
            gb.getADate(),
            gb.getCUser(),
            gb.getCDate(),
            gb.getOrgCode(),
            gb.getGlNo()
    );
}
	
	    public List<Map<String,Object>> getGlList(){
	        String sql = "SELECT GLNO, GLNAME FROM GL102";
	        return jdbcTemplate.queryForList(sql);
	    }
}

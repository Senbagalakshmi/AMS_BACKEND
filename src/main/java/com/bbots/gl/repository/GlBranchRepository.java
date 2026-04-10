package com.bbots.gl.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.bbots.gl.model.GlBranch;

@Repository
public class GlBranchRepository {

    @Autowired
	private JdbcTemplate jdbcTemplate;

	
	
	private final RowMapper<GlBranch> glBranch = (rs, rowNum) -> new GlBranch(
			 rs.getLong("ORGCODE"),
			 rs.getInt("GLNO"),
			 rs.getString("ALLOWEDCURR"),
			 rs.getString("EUSER"),
			 rs.getTimestamp("EDATE"),
			 rs.getString("AUSER"),
			 rs.getTimestamp("ADATE"),
			 rs.getString("CUSER"),
			 rs.getTimestamp("CDATE")			          
	    );

	    public List<GlBranch> findAll() {
	        return jdbcTemplate.query("SELECT * FROM GL104", glBranch);
	    }

	    public List<GlBranch> findByUserId(Integer glNo) {
	        return jdbcTemplate.query("SELECT * FROM GL104 WHERE GLNO = ?", glBranch, glNo);
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

	    public void delete(Integer glNo) {
	        jdbcTemplate.update("DELETE FROM GL104 WHERE GLNO = ?",glNo);
	    }
	    
	    
	    public void update(GlBranch gb) {
	        String sql = "UPDATE GL104 SET " +
	                "ORGCODE = ?, " +
	                "GLNO = ?, " +
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
	        		gb.getOrgCode(),     // WHERE condition
	        		gb.getGlNo()      // WHERE condition
	        );
	    }
	
	
}

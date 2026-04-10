package com.bbots.gl.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.bbots.gl.model.GlTransation;

@Repository
public class GlTransactionRepository {
	
	@Autowired
	
	private JdbcTemplate jdbcTemplate;

	
	
	private final RowMapper<GlTransation> glTransation = (rs, rowNum) -> new GlTransation(
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

	    public List<GlTransation> findAll() {
	        return jdbcTemplate.query("SELECT * FROM GL103", glTransation);
	    }

	    public List<GlTransation> findByUserId(Integer glNo) {
	        return jdbcTemplate.query("SELECT * FROM GL103 WHERE GLNO = ?", glTransation, glNo);
	    }

	    public void save(GlTransation gt) {
	        String sql = "INSERT INTO GL103 (ORGCODE, GLNO, ALLOWEDCURR, EUSER, EDATE, AUSER, ADATE, CUSER, CDATE) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
	        jdbcTemplate.update(sql,
	        		gt.getOrgCode(),
	        		gt.getGlNo(),
	        		gt.getAllowedCurr(),
	        		gt.getEUser(),
	        		gt.getEDate(),
	        		gt.getAUser(),
	        		gt.getADate(),
	        		gt.getCUser(),
	        		gt.getCDate()
	        );
	    }

	    public void delete(Integer glNo) {
	        jdbcTemplate.update("DELETE FROM GL103 WHERE GLNO = ?",glNo);
	    }
	    
	    
	    public void update(GlTransation gt) {
	        String sql = "UPDATE GL103 SET " +
	                "ALLOWEDCURR = ?, " +	                
	                "EUSER = ?, " +
	                "EDATE = ?, " +
	                "AUSER = ?, " +
	                "ADATE = ?, " +
	                "CUSER = ?, " +
	                "CDATE = ? " +
	                "WHERE ORGCODE = ? AND GLNO = ?";

	        jdbcTemplate.update(sql,
	        		gt.getAllowedCurr(),
	        		gt.getEUser(),
	        		gt.getEDate(),
	        		gt.getAUser(),
	        		gt.getADate(),
	        		gt.getCUser(),
	        		gt.getCDate(),
	        		gt.getOrgCode(),     // WHERE condition
	        		gt.getGlNo()      // WHERE condition
	        );
	    }

}

package com.bbots.gl.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.bbots.gl.model.GlSegments;

@Repository
public class GlSegmentsRepository {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	
	
	private final RowMapper<GlSegments> glSegments = (rs, rowNum) -> new GlSegments(
			 rs.getInt("ORGCODE"),
			 rs.getInt("GLNO"),
			 rs.getString("SEGID"),
			 rs.getString("SEGVALUE"),
			 rs.getInt("SEGTYPE"),
			 rs.getString("EUSER"),
			 rs.getTimestamp("EDATE"),
			 rs.getString("AUSER"),
			 rs.getTimestamp("ADATE"),
			 rs.getString("CUSER"),
			 rs.getTimestamp("CDATE")			          
	    );

	    public List<GlSegments> findAll() {
	        return jdbcTemplate.query("SELECT * FROM GL105", glSegments);
	    }

	    public List<GlSegments> findByUserId(Integer glNo) {
	        return jdbcTemplate.query("SELECT * FROM GL105 WHERE GLNO = ?", glSegments, glNo);
	    }

	    public void save(GlSegments gs) {
	        String sql = "INSERT INTO GL105 (ORGCODE, GLNO, SEGID, SEGVALUE, SEGTYPE, EUSER, EDATE, AUSER, ADATE, CUSER, CDATE) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	        jdbcTemplate.update(sql,
	        		gs.getOrgCode(),
	        		gs.getGlNo(),
	        		gs.getSegId(),
	        		gs.getSegValue(),
	        		gs.getSegType(),
	        		gs.getEUser(),
	        		gs.getEDate(),
	        		gs.getAUser(),
	        		gs.getADate(),
	        		gs.getCUser(),
	        		gs.getCDate()
	        );
	    }

	    public void delete(Integer glNo) {
	        jdbcTemplate.update("DELETE FROM GL105 WHERE GLNO = ?",glNo);
	    }
	    
	    
	    public void update(GlSegments gs) {
	        String sql = "UPDATE GL105 SET " +
	                "ORGCODE = ?, " +
	                "GLNO = ?, " +
	                "SEGID = ?, " +
	                "SEGVALUE = ?, " +
	                "SEGTYPE = ?, " +
	                "EUSER = ?, " +
	                "EDATE = ?, " +
	                "AUSER = ?, " +
	                "ADATE = ?, " +
	                "CUSER = ?, " +
	                "CDATE = ? " +
	                "WHERE ORGCODE = ? AND GLNO = ?";

	        jdbcTemplate.update(sql,
	        		gs.getSegId(),
	        		gs.getSegValue(),
	        		gs.getSegType(),
	        		gs.getEUser(),
	        		gs.getEDate(),
	        		gs.getAUser(),
	        		gs.getADate(),
	        		gs.getCUser(),
	        		gs.getCDate(),
	        		gs.getOrgCode(),     // WHERE condition
	        		gs.getGlNo()      // WHERE condition
	        );
	    }

}

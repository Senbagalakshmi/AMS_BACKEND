package com.bbots.gl.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.bbots.gl.model.GlAttributes;

@Repository
public class GlAttributesRepository {
	
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	
	
	private final RowMapper<GlAttributes> glAttributes = (rs, rowNum) -> new GlAttributes(
			 rs.getLong("ORGCODE"),
			 rs.getInt("GLNO"),
			 rs.getString("GLATTRID"),
			 rs.getString("GLATTRVALUE"),
			 rs.getString("EUSER"),
			 rs.getTimestamp("EDATE"),
			 rs.getString("AUSER"),
			 rs.getTimestamp("ADATE"),
			 rs.getString("CUSER"),
			 rs.getTimestamp("CDATE")			          
	    );

	    public List<GlAttributes> findAll() {
	        return jdbcTemplate.query("SELECT * FROM GL106", glAttributes);
	    }

	    public List<GlAttributes> findByUserId(Integer glNo) {
	        return jdbcTemplate.query("SELECT * FROM GL106 WHERE GLNO = ?", glAttributes, glNo);
	    }

	    public void save(GlAttributes ga) {
	        String sql = "INSERT INTO GL106 (ORGCODE, GLNO, GLATTRID, GLATTRVALUE, EUSER, EDATE, AUSER, ADATE, CUSER, CDATE) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	        jdbcTemplate.update(sql,
	        		ga.getOrgCode(),
	        		ga.getGlNo(),
	        		ga.getGlAttrid(),
	        		ga.getGlAttrValue(),
	        		ga.getEUser(),
	        		ga.getEDate(),
	        		ga.getAUser(),
	        		ga.getADate(),
	        		ga.getCUser(),
	        		ga.getCDate()
	        );
	    }

	    public void delete(Integer glNo) {
	        jdbcTemplate.update("DELETE FROM GL106 WHERE GLNO = ?",glNo);
	    }
	    
	    
	    public void update(GlAttributes ga) {
	        String sql = "UPDATE GL106 SET " +
	                "ORGCODE = ?, " +
	                "GLNO = ?, " +
	                "GLATTRID = ?, " +
	                "GLATTRVALUE = ?, " +	        
	                "EUSER = ?, " +
	                "EDATE = ?, " +
	                "AUSER = ?, " +
	                "ADATE = ?, " +
	                "CUSER = ?, " +
	                "CDATE = ? " +
	                "WHERE ORGCODE = ? AND GLNO = ?";

	        jdbcTemplate.update(sql,
	        		ga.getGlAttrid(),
	        		ga.getGlAttrValue(),
	        		ga.getEUser(),
	        		ga.getEDate(),
	        		ga.getAUser(),
	        		ga.getADate(),
	        		ga.getCUser(),
	        		ga.getCDate(),
	        		ga.getOrgCode(),     // WHERE condition
	        		ga.getGlNo()      // WHERE condition
	        );
	    }


}

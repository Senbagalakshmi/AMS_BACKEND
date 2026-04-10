package com.bbots.gl.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.bbots.gl.model.GlMaster;

@Repository
public class GlMasterRepository {
	
	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	
	private final RowMapper<GlMaster> glMaster = (rs, rowNum) -> new GlMaster(
			 rs.getLong("ORGCODE"),
			 rs.getInt("GLNO"),
			 rs.getString("GLNAME"),
			 rs.getInt("GLCATCD"),
			 rs.getInt("STATUS"),
			 rs.getString("EUSER"),
			 rs.getTimestamp("EDATE"),
			 rs.getString("AUSER"),
			 rs.getTimestamp("ADATE"),
			 rs.getString("CUSER"),
			 rs.getTimestamp("CDATE")			          
	    );

	    public List<GlMaster> findAll() {
	        return jdbcTemplate.query("SELECT * FROM GL102", glMaster);
	    }

	    public List<GlMaster> findByUserId(Integer glNo) {
	        return jdbcTemplate.query("SELECT * FROM GL102 WHERE GLNO = ?", glMaster, glNo);
	    }

	    public void save(GlMaster gm) {
	        String sql = "INSERT INTO GL102 (ORGCODE, GLNO, GLNAME, GLCATCD, STATUS, EUSER, EDATE, AUSER, ADATE, CUSER, CDATE) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	        jdbcTemplate.update(sql,
	        		gm.getOrgCode(),
	        		gm.getGlNo(),
	        		gm.getGlName(),
	        		gm.getGlCatCd(),
	        		gm.getStatus(),
	        		gm.getEUser(),
	        		gm.getEDate(),
	        		gm.getAUser(),
	        		gm.getADate(),
	        		gm.getCUser(),
	        		gm.getCDate()
	        );
	    }

	    public void delete(Integer glNo) {
	        jdbcTemplate.update("DELETE FROM GL102 WHERE GLNO = ?",glNo);
	    }
	    
	    
	    public void update(GlMaster gm) {
	        String sql = "UPDATE GL102 SET " +
	                "ORGCODE = ?, " +
	                "GLNO = ?, " +
	                "GLNAME = ?, " +
	                "GLCATCD = ?, " +
	                "STATUS = ?, " +
	                "EUSER = ?, " +
	                "EDATE = ?, " +
	                "AUSER = ?, " +
	                "ADATE = ?, " +
	                "CUSER = ?, " +
	                "CDATE = ? " +
	                "WHERE ORGCODE = ? AND GLNO = ?";

	        jdbcTemplate.update(sql,
	        		gm.getGlName(),
	        		gm.getGlCatCd(),
	        		gm.getStatus(),
	        		gm.getEUser(),
	        		gm.getEDate(),
	        		gm.getAUser(),
	        		gm.getADate(),
	        		gm.getCUser(),
	        		gm.getCDate(),
	        		gm.getOrgCode(),     // WHERE condition
	        		gm.getGlNo()      // WHERE condition
	        );
	    }


}

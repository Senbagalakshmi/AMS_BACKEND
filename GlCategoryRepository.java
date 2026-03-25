package com.bbots.gl.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.bbots.gl.model.GlCategory;


@Repository
public class GlCategoryRepository {
	
	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	 private final RowMapper<GlCategory> glCategory = (rs, rowNum) -> new GlCategory(
			 rs.getInt("ORGCODE"),
			 rs.getInt("GLCATCD"),
			 rs.getString("GLCATNAME"),
			 rs.getString("GLCATTYPE"),
			 rs.getString("GLCATSUBTYPE"),
			 rs.getString("EUSER"),
			 rs.getTimestamp("EDATE"),
			 rs.getString("AUSER"),
			 rs.getTimestamp("ADATE"),
			 rs.getString("CUSER"),
			 rs.getTimestamp("CDATE")			          
	    );

	    public List<GlCategory> findAll() {
	        return jdbcTemplate.query("SELECT * FROM GL_CATEGORY", glCategory);
	    }

	    public List<GlCategory> findByUserId(Integer glCatCd) {
	        return jdbcTemplate.query("SELECT * FROM GL_CATEGORY WHERE GLCATCD = ?", glCategory, glCatCd);
	    }

	    public void save(GlCategory gl) {
	        String sql = "INSERT INTO GL_CATEGORY (ORGCODE, GLCATCD, GLCATNAME, GLCATTYPE, GLCATSUBTYPE, EUSER, EDATE, AUSER, ADATE, CUSER, CDATE) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	        jdbcTemplate.update(sql,
	                gl.getOrgCode(),
	                gl.getGlCatCd(),
	                gl.getGlCatName(),
	                gl.getGlCatType(),
	                gl.getGlCatSubType(),
	                gl.getEUser(),
	                gl.getEDate(),
	                gl.getAUser(),
	                gl.getADate(),
	                gl.getCUser(),
	                gl.getCDate()
	        );
	    }

	    public void delete(Integer glCatCd) {
	        jdbcTemplate.update("DELETE FROM GL_CATEGORY WHERE GLCATCD = ?",glCatCd);
	    }

}

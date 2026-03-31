package com.bbots.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

import com.bbots.model.Auth101Config;
import com.bbots.model.AuthConfigDTO;
import com.bbots.model.AuthDataBlock;
import com.bbots.model.AuthLevelDTO;
import com.bbots.model.AuthRecord;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class AuthRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // --- AUTH101 Mappers ---
    private final RowMapper<Auth101Config> configMapper = (rs, rowNum) -> new Auth101Config(
            rs.getString("PROGRAM_ID"),
            rs.getString("PROGRAM_NAME"),
            rs.getBoolean("APPROVAL_REQ"),
            rs.getBoolean("PRE_APPROVE_PROC"),
            rs.getString("PRE_EXEC_METHOD"),
            rs.getString("PRE_PROCESS_NAME"),
            rs.getBoolean("POST_APPROVE_PROC"),
            rs.getString("POST_EXEC_METHOD"),
            rs.getString("POST_PROCESS_NAME"),
            rs.getBoolean("IS_TRAN"),
            rs.getInt("AUTH_LEVELS")
    );

    // --- AUTH101 Methods ---
    public List<Auth101Config> getAllConfigs() {
        return jdbcTemplate.query("SELECT * FROM AUTH101", configMapper);
    }

    public Auth101Config getConfigById(String id) {
        return jdbcTemplate.queryForObject("SELECT * FROM AUTH101 WHERE PROGRAM_ID = ?", configMapper, id);
    }

    public void updateConfig(Auth101Config cfg) {
        String sql = "UPDATE AUTH101 SET APPROVAL_REQ = ?, PRE_APPROVE_PROC = ?, PRE_EXEC_METHOD = ?, " +
                     "PRE_PROCESS_NAME = ?, POST_APPROVE_PROC = ?, POST_EXEC_METHOD = ?, " +
                     "POST_PROCESS_NAME = ?, AUTH_LEVELS = ? WHERE PROGRAM_ID = ?";
        jdbcTemplate.update(sql, cfg.isApprovalReq(), cfg.isPreApproveProc(), cfg.getPreExecMethod(),
                cfg.getPreProcessName(), cfg.isPostApproveProc(), cfg.getPostExecMethod(),
                cfg.getPostProcessName(), cfg.getLevels(), cfg.getId());
    }

    // --- AUTH001/002 Methods ---
    public List<AuthRecord> getQueue() {
        String sql = "SELECT * FROM AUTH001 WHERE FLUSER = '0' OR SLUSER = '0' OR TLUSER = '0' ORDER BY EDATE DESC";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            AuthRecord record = new AuthRecord();
            record.setOrgCode(rs.getLong("ORGCODE"));
            record.setProgramId(rs.getString("PROGRAMID"));
            record.setAuthSl(rs.getLong("AUTHSL"));
            record.setDisplayRemarks(rs.getString("DISPLAY_REMARKS"));
            record.setFlUser(rs.getInt("FLUSER"));
            record.setEntryUser(rs.getString("EUSER"));
            record.setEntryDate(rs.getTimestamp("EDATE"));
            // Fetch blocks
            record.setDataBlocks(getDataBlocks(record.getAuthSl()));
            return record;
        });
    }

    public String getProgramId(Long authSl) {
        String sql = "SELECT PROGRAMID FROM AUTH001 WHERE AUTHSL = ?";
        return jdbcTemplate.queryForObject(sql, String.class, authSl);
    }

    public List<AuthDataBlock> getDataBlocks(Long authSl) {
        return jdbcTemplate.query("SELECT * FROM AUTH002 WHERE AUTHSL = ?", (rs, rowNum) -> {
            AuthDataBlock block = new AuthDataBlock();
            block.setOrgCode(rs.getLong("ORGCODE"));
            block.setEffDate(rs.getDate("EFFDATE"));
            block.setProgramId(rs.getString("PROGRAMID"));
            block.setPrimaryKey(rs.getString("PRIMARYKEY"));
            block.setAuthSl(rs.getLong("AUTHSL"));
            block.setRecSl(rs.getInt("RECSL"));
            block.setTableName(rs.getString("TABLENAME"));
            block.setDataBlock(rs.getString("DATABLOCK"));
            return block;
        }, authSl);
    }
    
    public void insertAuthRequest(AuthRecord record) {
        String sql001 = "INSERT INTO AUTH001 (ORGCODE, PROGRAMID, DISPLAY_REMARKS, " +
                        "FLUSER, FLDATE, SLUSER, SLDATE, TLUSER, TLDATE, EUSER, EDATE) " +
                        "VALUES (?, ?, ?, '0', NULL, '0', NULL, '0', NULL, ?, CURRENT_TIMESTAMP) " +
                        "RETURNING AUTHSL";
                        
        Long authSl = jdbcTemplate.queryForObject(sql001, Long.class, 
            record.getOrgCode(), record.getProgramId(), record.getDisplayRemarks(), record.getEntryUser());
            
        if (record.getDataBlocks() != null && authSl != null) {
            String sql002 = "INSERT INTO AUTH002 (ORGCODE, EFFDATE, PROGRAMID, PRIMARYKEY, AUTHSL, RECSL, TABLENAME, DATABLOCK) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            for (AuthDataBlock block : record.getDataBlocks()) {
                jdbcTemplate.update(sql002, 
                    block.getOrgCode(), block.getEffDate(), block.getProgramId(), block.getPrimaryKey(),
                    authSl, block.getRecSl(), block.getTableName(), block.getDataBlock());
            }
        }
    }

    public void processAuth(Long authSl, int level, String userId, int status) {
        String sql = "CALL pr_process_approval(?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, authSl, level, SecurityContextHolder.getContext().getAuthentication().getName(), userId, status);
    }

    // --- AUTHCTL Configuration Methods ---
    public List<AuthConfigDTO> getAllAuthConfigs() {
        String sql101 = "SELECT * FROM AUTH101";
        return jdbcTemplate.query(sql101, (rs, rowNum) -> {
            AuthConfigDTO dto = new AuthConfigDTO();
            dto.setOrgCode(rs.getLong("ORGCODE"));
            dto.setProgramId(rs.getString("PROGRAMID"));
            dto.setApprovalReq(rs.getInt("APPROVALREQ"));
            dto.setPreApproveProc(rs.getInt("PRE_APPROVE_PROC"));
            dto.setPreExecMethod(rs.getString("PRE_EXEC_METHOD"));
            dto.setPreProcessName(rs.getString("PRE_PROCESSNAME"));
            dto.setPostApproveProc(rs.getInt("POST_APPROVE_PROC"));
            dto.setPostExecMethod(rs.getString("POST_EXEC_METHOD"));
            dto.setPostProcessName(rs.getString("POST_PROCESSNAME"));
            dto.setIsTranPgm(rs.getInt("ISTRANPGM"));
            
            String sql102 = "SELECT * FROM AUTH102 WHERE PROGRAMID = ?";
            List<AuthLevelDTO> levels = jdbcTemplate.query(sql102, (rs2, rowNum2) -> {
                AuthLevelDTO lvl = new AuthLevelDTO();
                lvl.setPermissiontype(rs2.getString("PERMISSIONTYPE"));
                lvl.setLevel(rs2.getInt("LEVELS"));
                lvl.setRolecd(rs2.getString("ROLECD"));
                lvl.setUserid(rs2.getString("USERID"));
                return lvl;
            }, dto.getProgramId());
            dto.setAuthLevels(levels);
            return dto;
        });
    }

    public void createAuthConfig(AuthConfigDTO dto) {
        // Delete existing configurations for this program
        jdbcTemplate.update("DELETE FROM AUTH102 WHERE PROGRAMID = ?", dto.getProgramId());
        jdbcTemplate.update("DELETE FROM AUTH101 WHERE PROGRAMID = ?", dto.getProgramId());

        String sql101 = "INSERT INTO AUTH101 (ORGCODE, PROGRAMID, APPROVALREQ, PRE_APPROVE_PROC, PRE_EXEC_METHOD, " +
                        "PRE_PROCESSNAME, POST_APPROVE_PROC, POST_EXEC_METHOD, POST_PROCESSNAME, ISTRANPGM, EUSER, EDATE) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 'SYSTEM', CURRENT_TIMESTAMP)";
        jdbcTemplate.update(sql101, 
            dto.getOrgCode(), dto.getProgramId(), dto.getApprovalReq(), dto.getPreApproveProc(), dto.getPreExecMethod(),
            dto.getPreProcessName(), dto.getPostApproveProc(), dto.getPostExecMethod(), dto.getPostProcessName(),
            dto.getIsTranPgm());

        if (dto.getAuthLevels() != null) {
            String sql102 = "INSERT INTO AUTH102 (ORGCODE, PROGRAMID, PERMISSIONTYPE, LEVELS, ROLECD, USERID, EUSER, EDATE) " +
                            "VALUES (?, ?, ?, ?, ?, ?, 'SYSTEM', CURRENT_TIMESTAMP)";
            for (AuthLevelDTO level : dto.getAuthLevels()) {
                jdbcTemplate.update(sql102,
                    dto.getOrgCode(), dto.getProgramId(), level.getPermissiontype(), level.getLevel(), level.getRolecd(), level.getUserid());
            }
        }
    }
}

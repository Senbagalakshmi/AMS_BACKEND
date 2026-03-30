package com.bbots.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthRecord {
    private Long orgCode;
    private String programId;
    private Long authSl;
    private String displayRemarks;
    private String flUser;
    private String flUserId;
    private Date flUserDate;
    private String slUser;
    private String slUserId;
    private Date slUserDate;
    private String tlUser;
    private String tlUserId;
    private Date tlUserDate;
    private String entryUser;
    private Date entryDate;
    private boolean correctionReq;
    private String correctionDlts;
    private List<AuthDataBlock> dataBlocks;
}

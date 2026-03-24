package com.bbots.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthDataBlock {
    private String orgCode;
    private Date effDate;
    private String programId;
    private String primaryKey;
    private Long authSl;
    private int recSl;
    private String tableName;
    private String dataBlock;
}

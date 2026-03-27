package com.bbots.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Menu001 {
    private Long orgCode;
    private String pgmId;
    private String descn;
    private Integer module;
    private Integer subModule;
    private Integer pgmClass;
    private Integer status;
    private String remarks;
    private Date eDate;
    private String eUser;
}

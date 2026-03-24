package com.bbots.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    private Integer orgCode;
    private Integer roleCd;
    private String roleName;
    private String roleType;
    private String roleSubtype;
    private Integer viewAccess;
    private Integer authAccess;
    private Integer makerAccess;
    private Integer adminAccess;
    private Integer sysAdminAccess;
    private String eUser;
    private Date eDate;
    private String aUser;
    private Date aDate;
    private String cUser;
    private Date cDate;
}

package com.bbots.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRoleAssign {
    private Integer orgCode;
    private String userScd;
    private Integer roleCd;
    private String eUser;
    private Date eDate;
    private String aUser;
    private Date aDate;
    private String cUser;
    private Date cDate;
}

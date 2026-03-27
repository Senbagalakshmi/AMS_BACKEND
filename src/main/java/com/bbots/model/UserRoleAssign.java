package com.bbots.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRoleAssign {
    @com.fasterxml.jackson.annotation.JsonProperty("orgCode")
    private Long orgcode;
    @com.fasterxml.jackson.annotation.JsonProperty("usersCd")
    private String userscd;
    @com.fasterxml.jackson.annotation.JsonProperty("roleCd")
    private Integer rolecd;
    
    @com.fasterxml.jackson.annotation.JsonProperty("roleName")
    @com.fasterxml.jackson.annotation.JsonAlias({"roleName", "rolename", "role_name"})
    private String roleName;

    @com.fasterxml.jackson.annotation.JsonProperty("eUser")
    private String euser;
    @com.fasterxml.jackson.annotation.JsonProperty("eDate")
    private java.util.Date edate;
    private String aUser;
    private Date aDate;
    private String cUser;
    private Date cDate;
}

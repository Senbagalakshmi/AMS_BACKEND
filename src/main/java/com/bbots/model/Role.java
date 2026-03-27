package com.bbots.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    @com.fasterxml.jackson.annotation.JsonProperty("orgCode")
    private Long orgcode;
    @com.fasterxml.jackson.annotation.JsonProperty("roleCd")
    private Integer rolecd;
    @com.fasterxml.jackson.annotation.JsonProperty("roleName")
    private String rolename;
    @com.fasterxml.jackson.annotation.JsonProperty("roleType")
    private String roletype;
    @com.fasterxml.jackson.annotation.JsonProperty("roleSubType")
    private String rolesubtype;
    @com.fasterxml.jackson.annotation.JsonProperty("viewAccess")
    private Integer viewaccess;
    @com.fasterxml.jackson.annotation.JsonProperty("authAccess")
    private Integer authaccess;
    @com.fasterxml.jackson.annotation.JsonProperty("makerAccess")
    private Integer makeraccess;
    @com.fasterxml.jackson.annotation.JsonProperty("adminAccess")
    private Integer adminaccess;
    @com.fasterxml.jackson.annotation.JsonProperty("sysAdminAccess")
    private Integer sysadminaccess;
    private String eUser;
    private Date eDate;
    private String aUser;
    private Date aDate;
    private String cUser;
    private Date cDate;
}

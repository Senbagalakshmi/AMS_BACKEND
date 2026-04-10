package com.bbots.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Organisation {

    private Long orgcode;
    private String name;
    
    @com.fasterxml.jackson.annotation.JsonProperty("openDate")
    private Date opendate;
    
    private String country;
    
    @com.fasterxml.jackson.annotation.JsonProperty("divisionName")
    private String divisionname;
    
    private String pincode;
    private String addrline1;
    private String addrline2;
    private String addrline3;
    private String addrline4;
    private String addrline5;
    private String telephone;
    private String email;
    private Integer status;
    
    @com.fasterxml.jackson.annotation.JsonProperty("indiv")
    private Integer indiv;
    
    @com.fasterxml.jackson.annotation.JsonProperty("eUser")
    private String euser;
    
    @com.fasterxml.jackson.annotation.JsonProperty("eDate")
    private Date edate;
    
    @com.fasterxml.jackson.annotation.JsonProperty("aUser")
    private String auser;
    
    @com.fasterxml.jackson.annotation.JsonProperty("aDate")
    private Date adate;
    
    @com.fasterxml.jackson.annotation.JsonProperty("cUser")
    private String cuser;
    
    @com.fasterxml.jackson.annotation.JsonProperty("cDate")
    private Date cdate;
    
    private String logo; // FTP path
}

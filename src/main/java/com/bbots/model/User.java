package com.bbots.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @com.fasterxml.jackson.annotation.JsonProperty("orgCode")
    private Long orgcode;
    @com.fasterxml.jackson.annotation.JsonProperty("usersCd")
    private String userscd;
    @com.fasterxml.jackson.annotation.JsonProperty("menuType")
    private Integer menutype;
    private String gender;
    private String title;
    @com.fasterxml.jackson.annotation.JsonProperty("fName")
    private String fname;
    @com.fasterxml.jackson.annotation.JsonProperty("mName")
    private String mname;
    @com.fasterxml.jackson.annotation.JsonProperty("lName")
    private String lname;
    private String email;
    private String mobile;
    private String country;
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
    @com.fasterxml.jackson.annotation.JsonProperty("clientCd")
    private String clientcd;
}

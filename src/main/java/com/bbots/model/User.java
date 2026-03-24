package com.bbots.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Integer orgCode;
    private String userScd;
    private Integer menuType;
    private String gender;
    private String title;
    private String fName;
    private String mName;
    private String lName;
    private String email;
    private String mobile;
    private String country;
    private String eUser;
    private Date eDate;
    private String aUser;
    private Date aDate;
    private String cUser;
    private Date cDate;
    private String clientCd;
}

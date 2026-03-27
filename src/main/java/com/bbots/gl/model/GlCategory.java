package com.bbots.gl.model;


import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class GlCategory {
	
	private int orgCode;
    private int glCatCd;
    private String glCatName;
    private String glCatType;
    private String glCatSubType;
    private String eUser;
    private Date eDate;
    private String aUser;
    private Date aDate;
    private String cUser;
    private Date cDate;
    
    
    

}

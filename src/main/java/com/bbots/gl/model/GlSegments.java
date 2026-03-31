package com.bbots.gl.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GlSegments {
	
	private int orgCode;
    private int glNo;
    private String segId;
    private String segValue;
    private int segType;
    private String eUser;
    private Date eDate;
    private String aUser;
    private Date aDate;
    private String cUser;
    private Date cDate;

}

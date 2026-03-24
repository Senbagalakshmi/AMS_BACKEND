package com.bbots.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubModule {
    private Integer moduleId;
    private Integer subModuleId;
    private String subModuleName;
    private Integer status;
    private Date eDate;
    private String eUser;
    private Date cDate;
    private String cUser;
    private Date aDate;
    private String aUser;
}

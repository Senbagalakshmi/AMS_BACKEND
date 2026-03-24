package com.bbots.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Module {
    private Integer moduleId;
    private String moduleName;
    private Integer subModuleRequired; // 1: Yes, 0: No
    private Integer status; // 1: Enable, 0: Disable
    private Date eDate;
    private String eUser;
    private Date cDate;
    private String cUser;
    private Date aDate;
    private String aUser;
}

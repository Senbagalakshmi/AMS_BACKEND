package com.bbots.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Menu004 {
    private Long orgCode;
    private Integer roleCd;
    private Integer menuCode;
    private Integer subMenuCode;
    private String pgmId;
    private String description;
    private Integer menuOrder;
    private String programPath;
    private String menuLogo;
    private Integer status;
    private Date eDate;
    private String eUser;
}

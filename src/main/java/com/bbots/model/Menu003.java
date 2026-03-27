package com.bbots.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Menu003 {
    private Long orgCode;
    private Integer roleCd;
    private Integer menuCode;
    private Integer subMenuCode;
    private String description;
    private Integer menuOrder;
    private String subMenuPgmId;
    private String programPath;
    private String menuLogo;
    private Integer menuStatus;
    private Date eDate;
    private String eUser;
}

package com.bbots.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Menu002 {
    private Long orgCode;
    private Integer roleCd;
    private Integer menuCode;
    private String menuDescn;
    private Integer menuOrder;
    private Integer subMenuReq;
    private String parentMenuPgmId;
    private String programPath;
    private String menuLogo;
    private String menuLocation;
    private Integer menuStatus;
    private Date eDate;
    private String eUser;
}

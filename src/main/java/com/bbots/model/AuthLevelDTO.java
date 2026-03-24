package com.bbots.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthLevelDTO {
    private String permissionType;
    private int level;
    private String roleCd;
    private String userId;
}

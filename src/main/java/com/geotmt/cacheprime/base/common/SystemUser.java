package com.geotmt.cacheprime.base.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SystemUser {

    private Long id;
    private String name;
    private String role;

}

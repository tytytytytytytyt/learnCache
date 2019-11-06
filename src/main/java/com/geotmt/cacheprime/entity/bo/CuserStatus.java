package com.geotmt.cacheprime.entity.bo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class CuserStatus implements Serializable {

    private static final long serialVersionUID = 1302584L;

    private String cuserId ;
    private int status;

}

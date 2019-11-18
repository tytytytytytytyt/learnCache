package com.geotmt.cacheprime.base.common;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
public class PayToken {

    long userId ;
    double money;
}

package com.geotmt.cacheprime.annotation;


import java.lang.annotation.*;


@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Limiter {

    int permitsPerSecond();

    int timeOut();
}

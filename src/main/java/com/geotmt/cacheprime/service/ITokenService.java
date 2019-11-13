package com.geotmt.cacheprime.service;

public interface ITokenService {

    public String putToken(String name);

    public String getUserByToken(String token);
}

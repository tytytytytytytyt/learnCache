package com.geotmt.cacheprime.service;

import com.geotmt.cacheprime.base.common.PayToken;

public interface ITokenService {

    String putToken(String name);

    String getUserByToken(String token);

    String getPayToken(PayToken payToken);
}

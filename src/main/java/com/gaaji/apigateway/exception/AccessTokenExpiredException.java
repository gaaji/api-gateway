package com.gaaji.apigateway.exception;

import static com.gaaji.apigateway.exception.GatewayErrorCode.ACCESS_TOKEN_EXPIRED;

public class AccessTokenExpiredException extends AbstractApiException{

    public AccessTokenExpiredException() {
        super(ACCESS_TOKEN_EXPIRED);
    }
}

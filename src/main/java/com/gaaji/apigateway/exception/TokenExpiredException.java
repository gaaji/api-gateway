package com.gaaji.apigateway.exception;

import static com.gaaji.apigateway.exception.GatewayErrorCode.ACCESS_TOKEN_EXPIRED;

public class TokenExpiredException extends AbstractApiException{

    public TokenExpiredException() {
        super(ACCESS_TOKEN_EXPIRED);
    }
}

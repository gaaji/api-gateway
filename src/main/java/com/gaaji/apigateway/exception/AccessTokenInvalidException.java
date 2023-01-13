package com.gaaji.apigateway.exception;

import static com.gaaji.apigateway.exception.GatewayErrorCode.ACCESS_TOKEN_INVALID;

public class AccessTokenInvalidException extends AbstractApiException{

    public AccessTokenInvalidException() {
        super(ACCESS_TOKEN_INVALID);
    }
}

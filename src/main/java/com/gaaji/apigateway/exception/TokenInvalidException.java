package com.gaaji.apigateway.exception;

import static com.gaaji.apigateway.exception.GatewayErrorCode.ACCESS_TOKEN_INVALID;

public class TokenInvalidException extends AbstractApiException{

    public TokenInvalidException() {
        super(ACCESS_TOKEN_INVALID);
    }
}

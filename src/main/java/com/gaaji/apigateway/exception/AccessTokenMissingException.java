package com.gaaji.apigateway.exception;

import static com.gaaji.apigateway.exception.GatewayErrorCode.ACCESS_TOKEN_MISSING;

public class AccessTokenMissingException extends AbstractApiException{

    public AccessTokenMissingException() {
        super(ACCESS_TOKEN_MISSING);
    }
}

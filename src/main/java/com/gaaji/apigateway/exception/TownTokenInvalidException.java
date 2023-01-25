package com.gaaji.apigateway.exception;

import static com.gaaji.apigateway.exception.GatewayErrorCode.TOWN_TOKEN_INVALID;

public class TownTokenInvalidException extends AbstractApiException{

    public TownTokenInvalidException() {
        super(TOWN_TOKEN_INVALID);
    }
}

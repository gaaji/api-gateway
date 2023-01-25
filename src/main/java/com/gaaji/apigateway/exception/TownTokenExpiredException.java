package com.gaaji.apigateway.exception;

import static com.gaaji.apigateway.exception.GatewayErrorCode.TOWN_TOKEN_EXPIRED;

public class TownTokenExpiredException extends AbstractApiException{

    public TownTokenExpiredException() {
        super(TOWN_TOKEN_EXPIRED);
    }
}

package com.gaaji.apigateway.exception;

import static com.gaaji.apigateway.exception.GatewayErrorCode.TOWN_TOKEN_MISSING;

public class TownTokenMissingException extends AbstractApiException{

    public TownTokenMissingException() {
        super(TOWN_TOKEN_MISSING);
    }
}

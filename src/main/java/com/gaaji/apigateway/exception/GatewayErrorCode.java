package com.gaaji.apigateway.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum GatewayErrorCode implements ErrorCode{



    UNHANDLED_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "G-0001",
            "현재 해당 서비스가 정상 가동하기 힘든 상태입니다. "
                    + "오류 발생 상황과 오류 코드를 관리자 이메일로 "
                    + "전송해주시면 빠르게 조치하겠습니다."),

    ACCESS_TOKEN_MISSING(HttpStatus.UNAUTHORIZED, "G-0002",
            "로그인 후 이용해주세요."),
    ACCESS_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "G-0003",
            "엑세스 토큰이 만료되었습니다."),
    ACCESS_TOKEN_INVALID(HttpStatus.UNAUTHORIZED, "G-0004",
            "엑세스 토큰이 유효하지 않습니다."),
    TOWN_TOKEN_MISSING(HttpStatus.UNAUTHORIZED, "G-0005",
            "동네 토큰이 존재하지 않습니다."),
    TOWN_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "G-0006",
            "동네 토큰이 만료되었습니다."),
    TOWN_TOKEN_INVALID(HttpStatus.UNAUTHORIZED, "G-0007",
            "동네 토큰이 유효하지 않습니다."),

    ;

    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String errorMessage;

    public String getErrorName() {
        return this.name();
    }
}
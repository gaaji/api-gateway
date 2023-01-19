package com.gaaji.apigateway.exception;

import static com.gaaji.apigateway.exception.GatewayErrorCode.UNHANDLED_SERVER_ERROR;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.charset.StandardCharsets;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Order(-1)
public class GlobalWebExceptionHandler implements ErrorWebExceptionHandler {

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {

        ServerHttpResponse response = exchange.getResponse();
        ServerHttpRequest request = exchange.getRequest();
        ErrorResponse errorResponse;
        if (ex instanceof AbstractApiException) {

            errorResponse = ErrorResponse.createErrorResponse(
                    (AbstractApiException) ex,
                    String.valueOf(request.getPath()));
            response.setStatusCode( ((AbstractApiException) ex).getHttpStatus());


        }
        else{
            errorResponse = ErrorResponse.createErrorResponse(UNHANDLED_SERVER_ERROR,
                    String.valueOf(request.getPath()));
            response.setStatusCode(UNHANDLED_SERVER_ERROR.getHttpStatus());
        }


        byte[] bytes = errorResponse.toString().getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = response.bufferFactory().wrap(bytes);

        return response.writeWith(Flux.just(buffer));
    }
}

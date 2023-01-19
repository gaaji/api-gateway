package com.gaaji.apigateway.config;

import com.gaaji.apigateway.exception.GlobalWebExceptionHandler;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExceptionHandlerConfig {

//    @Bean
//    public ErrorWebExceptionHandler globalWebExceptionHandler(){
//        return new GlobalWebExceptionHandler();
//    }

}

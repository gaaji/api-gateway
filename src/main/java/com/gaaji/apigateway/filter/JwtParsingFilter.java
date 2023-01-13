package com.gaaji.apigateway.filter;

import com.gaaji.apigateway.exception.AccessTokenExpiredException;
import com.gaaji.apigateway.exception.AccessTokenInvalidException;
import com.gaaji.apigateway.exception.AccessTokenMissingException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import lombok.Data;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.annotation.AliasFor;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


@Component
public class JwtParsingFilter extends AbstractGatewayFilterFactory<JwtParsingFilter.Config> {

    private final Environment env;

    private final String secretKey;

    public JwtParsingFilter(Environment env) {
        super(Config.class);
        this.env = env;
        secretKey = Base64.getEncoder().encodeToString(env.getProperty("key.jwt")
                .getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) ->{
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();

            if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                throw new AccessTokenMissingException();
            }


            String accessToken = request.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0)
                    .replace("Bearer ", "");

            String authId = parseJwt(accessToken);

            request = exchange.getRequest().mutate().header("authId", authId)
                    .build();

            //Custom Post Filter
            return chain.filter(exchange.mutate().request(request).build());
        };
    }

    private String parseJwt(String token){
        Claims claims = Jwts.parser().setSigningKey(secretKey)
                .parseClaimsJws(token).getBody();

        if (!claims.getExpiration().after(new Date())) {
            throw new AccessTokenExpiredException();
        }

        if (!StringUtils.hasText(claims.getSubject())) {
            throw new AccessTokenInvalidException();
        }

        return claims.getSubject();
    }

    @Data
    public static class Config {

    }

}

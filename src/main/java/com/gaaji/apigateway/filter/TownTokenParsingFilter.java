package com.gaaji.apigateway.filter;

import com.gaaji.apigateway.exception.TokenExpiredException;
import com.gaaji.apigateway.exception.TokenInvalidException;
import com.gaaji.apigateway.exception.AccessTokenMissingException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequest.Builder;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Slf4j
@Component
public class TownTokenParsingFilter extends
        AbstractGatewayFilterFactory<TownTokenParsingFilter.Config> {

    private final Environment env;

    private final String secretKey;

    public TownTokenParsingFilter(Environment env) {
        super(Config.class);
        this.env = env;
        secretKey = Base64.getEncoder().encodeToString(env.getProperty("key.jwt")
                .getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();

            if (!request.getHeaders().containsKey("X-TOWN-TOKEN")) {
                throw new AccessTokenMissingException();
            }

            String townToken = request.getHeaders().get("X-TOWN-TOKEN").get(0);


            String parsedTownToken = parseJwt(townToken);


            request = exchange.getRequest().mutate().header("X-TOWN-TOKEN", parsedTownToken)
                    .build();


            //Custom Post Filter
            return chain.filter(exchange.mutate().request(request).build());
        };
    }

    private String parseJwt(String token) {
        Claims claims = Jwts.parser().setSigningKey(secretKey)
                .parseClaimsJws(token).getBody();

        if (!claims.getExpiration().after(new Date())) {
            throw new TokenExpiredException();
        }

        if (!StringUtils.hasText(claims.getSubject())) {
            throw new TokenInvalidException();
        }

        return claims.getSubject();
    }

    @Data
    public static class Config {

    }

}

package es.udc.tfg.app.rest.common;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtGeneratorImpl implements JwtGenerator {

    @Value("${jwt.signKey}")
    private String signKey;

    @Value("${jwt.expirationMinutes}")
    private long expirationMinutes;

    @Override
    public String generate(JwtInfo info) {
        return Jwts.builder()
                .claim("userId", info.getUserId())
                .claim("role", info.getRole())
                .setExpiration(new Date(System.currentTimeMillis() + expirationMinutes * 60 * 1000))
                .signWith(Keys.hmacShaKeyFor(signKey.getBytes()), SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public JwtInfo getInfo(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(signKey.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody();


        return new JwtInfo(
                (String) claims.get("role"),
                claims.getSubject(),
                ((Integer) claims.get("userId")).longValue()
                );
    }
}

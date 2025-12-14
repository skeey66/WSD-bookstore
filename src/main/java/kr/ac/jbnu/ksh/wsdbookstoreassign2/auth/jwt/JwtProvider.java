package kr.ac.jbnu.ksh.wsdbookstoreassign2.auth.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import java.util.Map;

@Component
public class JwtProvider {

    private final SecretKey key;
    private final int accessExpMin;
    private final int refreshExpDays;

    public JwtProvider(
            @Value("${app.jwt.secret}") String secret,
            @Value("${app.jwt.access-exp-min}") int accessExpMin,
            @Value("${app.jwt.refresh-exp-days}") int refreshExpDays
    ) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.accessExpMin = accessExpMin;
        this.refreshExpDays = refreshExpDays;
    }

    public String createAccessToken(Long userId, String role) {
        Instant now = Instant.now();
        Instant exp = now.plusSeconds(accessExpMin * 60L);

        return Jwts.builder()
                .subject(String.valueOf(userId))
                .claims(Map.of("role", role))
                .issuedAt(Date.from(now))
                .expiration(Date.from(exp))
                .signWith(key)
                .compact();
    }

    public String createRefreshToken(Long userId) {
        Instant now = Instant.now();
        Instant exp = now.plusSeconds(refreshExpDays * 86400L);

        return Jwts.builder()
                .subject(String.valueOf(userId))
                .issuedAt(Date.from(now))
                .expiration(Date.from(exp))
                .signWith(key)
                .compact();
    }

    public JwtParsed parse(String token) {
        var claims = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();

        Long userId = Long.parseLong(claims.getSubject());
        String role = claims.get("role", String.class); // refresh에는 null일 수 있음
        return new JwtParsed(userId, role);
    }

    public record JwtParsed(Long userId, String role) {}
}

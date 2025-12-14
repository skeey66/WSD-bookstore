package kr.ac.jbnu.ksh.wsdbookstoreassign2.auth.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
public class JwtTokenProvider {

    private final SecretKey key;
    private final long accessMinutes;
    private final long refreshDays;

    public JwtTokenProvider(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.access-minutes}") long accessMinutes,
            @Value("${jwt.refresh-days}") long refreshDays
    ) {
        // HS256이면 secret은 최소 32바이트(문자 32개 이상) 권장
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.accessMinutes = accessMinutes;
        this.refreshDays = refreshDays;
    }

    public String createAccessToken(Long userId, String email, String role) {
        Instant now = Instant.now();
        Instant exp = now.plusSeconds(accessMinutes * 60);

        return Jwts.builder()
                .subject(email) // sub
                .issuedAt(Date.from(now))
                .expiration(Date.from(exp))
                .claims(Map.of(
                        "uid", userId,
                        "role", role // "USER" / "ADMIN" 같은 값
                ))
                .signWith(key, Jwts.SIG.HS256)
                .compact();
    }

    public String createRefreshToken(Long userId, String email) {
        Instant now = Instant.now();
        Instant exp = now.plusSeconds(refreshDays * 24 * 60 * 60);

        return Jwts.builder()
                .subject(email)
                .issuedAt(Date.from(now))
                .expiration(Date.from(exp))
                .claims(Map.of(
                        "uid", userId,
                        "type", "refresh"
                ))
                .signWith(key, Jwts.SIG.HS256)
                .compact();
    }

    /** jjwt 0.12.x 방식 */
    public Claims parseClaims(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /** AccessToken 검증(서명/만료/형식 + refresh 토큰이면 거부) */
    public boolean validateAccessToken(String token) {
        try {
            Claims c = parseClaims(token);
            String type = c.get("type", String.class);
            return type == null; // refresh는 type=refresh라서 access로 쓰면 false
        } catch (ExpiredJwtException | MalformedJwtException | UnsupportedJwtException | IllegalArgumentException e) {
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    /** RefreshToken 검증(서명/만료/형식 + type=refresh 확인) */
    public boolean validateRefreshToken(String token) {
        try {
            Claims c = parseClaims(token);
            String type = c.get("type", String.class);
            return "refresh".equals(type);
        } catch (ExpiredJwtException | MalformedJwtException | UnsupportedJwtException | IllegalArgumentException e) {
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    /** 토큰에서 Authentication 생성(최소 구현: principal=email, 권한=ROLE_...) */
    public Authentication getAuthentication(String accessToken) {
        Claims c = parseClaims(accessToken);

        String email = c.getSubject();
        String role = c.get("role", String.class);

        String authority = (role == null || role.isBlank())
                ? "ROLE_USER"
                : (role.startsWith("ROLE_") ? role : "ROLE_" + role);

        return new UsernamePasswordAuthenticationToken(
                email,
                null,
                List.of(new SimpleGrantedAuthority(authority))
        );
    }

    public Long getUserId(String token) {
        Claims c = parseClaims(token);
        Object uid = c.get("uid");
        if (uid == null) return null;
        if (uid instanceof Number n) return n.longValue();
        return Long.valueOf(uid.toString());
    }

    public String getEmail(String token) {
        return parseClaims(token).getSubject();
    }
}

package kr.ac.jbnu.ksh.wsdbookstoreassign2.auth.jwt;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtTokenProvider tokenProvider;

    public JwtAuthFilter(JwtTokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws ServletException, IOException {

        String authHeader = req.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);

            try {
                // ✅ refresh 토큰을 access로 쓰는 것 방지
                if (!tokenProvider.validateAccessToken(token)) {
                    SecurityContextHolder.clearContext();
                    chain.doFilter(req, res);
                    return;
                }

                Claims claims = tokenProvider.parseClaims(token);

                // JWT: sub = email (유지)
                String email = claims.getSubject();
                String roleRaw = claims.get("role", String.class);

                // JWT: uid = userId (claims에서 꺼내기)
                Long uid = tokenProvider.getUserId(token);
                if (uid == null) {
                    SecurityContextHolder.clearContext();
                    chain.doFilter(req, res);
                    return;
                }

                // role 정규화: "USER" -> "ROLE_USER", "ROLE_ADMIN"은 그대로
                String roleClaim = claims.get("role", String.class);
                String authority = (roleRaw == null || roleRaw.isBlank())
                        ? "ROLE_USER"
                        : (roleRaw.startsWith("ROLE_") ? roleRaw : "ROLE_" + roleRaw);

                var authorities = List.of(new SimpleGrantedAuthority(authority));

                // ✅ principal을 userId로 넣어서 auth.getName() == "1" 같은 값이 되게 함
                var authentication = new UsernamePasswordAuthenticationToken(
                        email, // principal은 email로 유지해도 됨
                        null,
                        List.of(new SimpleGrantedAuthority(authority))
                );

                // ✅ email은 details로 보관 (필요하면 꺼내 쓰기)
                authentication.setDetails(uid);

                SecurityContextHolder.getContext().setAuthentication(authentication);

            } catch (Exception e) {
                SecurityContextHolder.clearContext();
            }
        }

        chain.doFilter(req, res);
    }
}

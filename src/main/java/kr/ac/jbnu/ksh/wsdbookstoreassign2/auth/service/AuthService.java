package kr.ac.jbnu.ksh.wsdbookstoreassign2.auth.service;

import io.jsonwebtoken.Claims;
import kr.ac.jbnu.ksh.wsdbookstoreassign2.auth.domain.RefreshToken;
import kr.ac.jbnu.ksh.wsdbookstoreassign2.auth.dto.TokenResponse;
import kr.ac.jbnu.ksh.wsdbookstoreassign2.auth.jwt.JwtTokenProvider;
import kr.ac.jbnu.ksh.wsdbookstoreassign2.auth.repository.RefreshTokenRepository;
import kr.ac.jbnu.ksh.wsdbookstoreassign2.user.domain.User;
import kr.ac.jbnu.ksh.wsdbookstoreassign2.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;

    public AuthService(
            UserRepository userRepository,
            RefreshTokenRepository refreshTokenRepository,
            PasswordEncoder passwordEncoder,
            JwtTokenProvider tokenProvider
    ) {
        this.userRepository = userRepository;
        this.refreshTokenRepository = refreshTokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
    }

    @Transactional
    public TokenResponse login(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("INVALID_CREDENTIALS"));

        if (!passwordEncoder.matches(password, user.getPasswordHash())) {
            throw new IllegalStateException("INVALID_CREDENTIALS");
        }

        String roleStr = "ROLE_" + user.getRole().name();

        String access = tokenProvider.createAccessToken(user.getId(), user.getEmail(), roleStr);
        String refresh = tokenProvider.createRefreshToken(user.getId(), user.getEmail());

        RefreshToken rt = new RefreshToken();
        rt.setUser(user);
        rt.setToken(refresh);

        Claims refreshClaims = tokenProvider.parseClaims(refresh);
        OffsetDateTime expiresAt = OffsetDateTime.ofInstant(
                refreshClaims.getExpiration().toInstant(),
                OffsetDateTime.now().getOffset()
        );

        rt.setExpiresAt(expiresAt);
        rt.setCreatedAt(OffsetDateTime.now());
        rt.setRevokedAt(null);

        refreshTokenRepository.save(rt);

        return new TokenResponse(access, refresh);
    }

    @Transactional(readOnly = true)
    public TokenResponse refresh(String refreshToken) {
        final Claims claims;
        try {
            claims = tokenProvider.parseClaims(refreshToken);
        } catch (Exception e) {
            // JWT 형식이 깨졌거나 파싱/서명 검증 실패 -> 401
            throw new IllegalStateException("INVALID_REFRESH_TOKEN");
        }

        Object type = claims.get("type");
        if (type == null || !"refresh".equals(String.valueOf(type))) {
            throw new IllegalStateException("INVALID_REFRESH_TOKEN");
        }

        String email = claims.getSubject();

        Object uidObj = claims.get("uid");
        if (uidObj == null) throw new IllegalStateException("INVALID_REFRESH_TOKEN");

        Long uid;
        try {
            uid = (uidObj instanceof Number n) ? n.longValue() : Long.valueOf(uidObj.toString());
        } catch (Exception e) {
            throw new IllegalStateException("INVALID_REFRESH_TOKEN");
        }

        // ✅ 핵심: DB에 있는 refresh 토큰만 허용 + revoke/expire면 401
        RefreshToken rt = refreshTokenRepository.findByToken(refreshToken)
                .orElseThrow(() -> new IllegalStateException("INVALID_REFRESH_TOKEN"));

        OffsetDateTime now = OffsetDateTime.now();
        if (rt.isRevoked() || rt.isExpired(now)) {
            throw new IllegalStateException("INVALID_REFRESH_TOKEN");
        }

        User user = userRepository.findById(uid)
                .orElseThrow(() -> new IllegalStateException("INVALID_REFRESH_TOKEN"));

        String roleStr = "ROLE_" + user.getRole().name();
        String newAccess = tokenProvider.createAccessToken(user.getId(), email, roleStr);

        return new TokenResponse(newAccess, refreshToken);
    }


    @Transactional
    public void logout(String refreshToken) {
        RefreshToken rt = refreshTokenRepository.findByToken(refreshToken)
                .orElseThrow(() -> new IllegalStateException("INVALID_REFRESH_TOKEN"));

        // 이미 revoke된 토큰이어도 로그아웃은 “성공(204)” 처리해도 됨(멱등)
        if (!rt.isRevoked()) {
            rt.setRevokedAt(OffsetDateTime.now());
            refreshTokenRepository.save(rt);
        }
    }
}

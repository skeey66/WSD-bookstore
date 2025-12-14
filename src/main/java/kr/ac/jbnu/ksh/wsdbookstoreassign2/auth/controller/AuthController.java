package kr.ac.jbnu.ksh.wsdbookstoreassign2.auth.controller;

import jakarta.validation.Valid;
import kr.ac.jbnu.ksh.wsdbookstoreassign2.auth.dto.*;
import kr.ac.jbnu.ksh.wsdbookstoreassign2.auth.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;

@Tag(name="Auth", description="인증/인가 API")
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Operation(summary = "로그인", description = "이메일/비밀번호로 로그인하고 Access Token과 Refresh Token을 발급합니다.")
    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody @Valid LoginRequest req) {
        return ResponseEntity.ok(authService.login(req.email(), req.password()));
    }

    @Operation(summary = "토큰 재발급", description = "Refresh Token으로 새로운 Access Token(필요 시 Refresh Token)을 재발급합니다. 만료/폐기된 토큰이면 401을 반환합니다.")
    @PostMapping("/refresh")
    public ResponseEntity<TokenResponse> refresh(@RequestBody @Valid RefreshRequest req) {
        return ResponseEntity.ok(authService.refresh(req.refreshToken()));
    }

    @Operation(summary = "로그아웃", description = "현재 사용자의 Refresh Token을 폐기하여 재발급이 불가능하도록 처리합니다.")
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody @Valid LogoutRequest req) {
        authService.logout(req.getRefreshToken());
        return ResponseEntity.noContent().build();
    }

}

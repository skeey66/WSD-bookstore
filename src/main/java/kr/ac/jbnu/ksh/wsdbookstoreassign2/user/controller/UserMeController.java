package kr.ac.jbnu.ksh.wsdbookstoreassign2.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import kr.ac.jbnu.ksh.wsdbookstoreassign2.user.domain.User;
import kr.ac.jbnu.ksh.wsdbookstoreassign2.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name="Users", description="사용자 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserMeController {

    private final UserRepository userRepository;

    @Operation(summary = "내 정보 조회", description = "현재 로그인한 사용자의 기본 정보(프로필/권한 등)를 조회합니다.")
    @GetMapping("/me")
    public MeResponse me(Authentication authentication) {
        // JwtTokenProvider.getAuthentication()에서 principal=email로 넣어둔 상태
        String email = (String) authentication.getPrincipal();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new java.util.NoSuchElementException("USER_NOT_FOUND"));

        return new MeResponse(user.getId(), user.getEmail(), user.getName(), user.getRole().name());
    }

    public record MeResponse(Long id, String email, String name, String role) {}
}

package kr.ac.jbnu.ksh.wsdbookstoreassign2.order.service;

import kr.ac.jbnu.ksh.wsdbookstoreassign2.common.exception.UnauthorizedException;
import kr.ac.jbnu.ksh.wsdbookstoreassign2.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CurrentUserResolver {

    private final UserRepository userRepository;

    public Long currentUserId(Authentication auth) {
        if (auth == null || !auth.isAuthenticated()) {
            throw new UnauthorizedException("Unauthenticated");
        }

        Long uid;
        // principal(email) vs details(userId) 둘 다 대응
        try {
            uid = Long.parseLong(auth.getName());
        } catch (NumberFormatException ignored) {
            Object details = auth.getDetails();
            if (details instanceof Long l) uid = l;
            else if (details instanceof Integer i) uid = i.longValue();
            else if (details != null) uid = Long.valueOf(details.toString());
            else throw new IllegalStateException("JWT uid not found in Authentication.details");
        }

        // ✅ 계정 비활성(deleted_at)인 경우 접근 차단
        var user = userRepository.findById(uid)
                .orElseThrow(() -> new UnauthorizedException("User not found"));
        if (user.getDeletedAt() != null) {
            throw new UnauthorizedException("Deactivated user");
        }

        return uid;
    }

    public boolean isAdmin(Authentication auth) {
        return auth != null && auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
    }
}

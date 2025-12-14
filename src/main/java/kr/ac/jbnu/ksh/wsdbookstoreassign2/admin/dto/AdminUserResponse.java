package kr.ac.jbnu.ksh.wsdbookstoreassign2.admin.dto;

import kr.ac.jbnu.ksh.wsdbookstoreassign2.user.domain.Role;
import kr.ac.jbnu.ksh.wsdbookstoreassign2.user.domain.User;

import java.time.OffsetDateTime;

public record AdminUserResponse(
        Long id,
        String email,
        String name,
        Role role,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt,
        OffsetDateTime deletedAt
) {
    public static AdminUserResponse from(User u) {
        return new AdminUserResponse(
                u.getId(),
                u.getEmail(),
                u.getName(),
                u.getRole(),
                u.getCreatedAt(),
                u.getUpdatedAt(),
                u.getDeletedAt()
        );
    }
}

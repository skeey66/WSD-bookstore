package kr.ac.jbnu.ksh.wsdbookstoreassign2.admin.dto;

import jakarta.validation.constraints.NotNull;
import kr.ac.jbnu.ksh.wsdbookstoreassign2.user.domain.Role;

public record AdminUserRoleUpdateRequest(
        @NotNull Role role
) {
}

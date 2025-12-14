package kr.ac.jbnu.ksh.wsdbookstoreassign2.user.dto;

import kr.ac.jbnu.ksh.wsdbookstoreassign2.user.domain.Role;

public record UserResponse(
        Long id,
        String email,
        String name,
        Role role
) {}

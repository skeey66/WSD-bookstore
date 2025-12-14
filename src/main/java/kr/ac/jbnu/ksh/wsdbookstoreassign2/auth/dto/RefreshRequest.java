package kr.ac.jbnu.ksh.wsdbookstoreassign2.auth.dto;

import jakarta.validation.constraints.NotBlank;

public record RefreshRequest(
        @NotBlank String refreshToken
) {}

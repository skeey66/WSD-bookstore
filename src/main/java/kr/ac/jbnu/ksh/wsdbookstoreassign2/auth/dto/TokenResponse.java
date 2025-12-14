package kr.ac.jbnu.ksh.wsdbookstoreassign2.auth.dto;

public record TokenResponse(
        String accessToken,
        String refreshToken
) {}

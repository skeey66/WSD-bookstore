package kr.ac.jbnu.ksh.wsdbookstoreassign2.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class LogoutRequest {
    @NotBlank
    private String refreshToken;
}

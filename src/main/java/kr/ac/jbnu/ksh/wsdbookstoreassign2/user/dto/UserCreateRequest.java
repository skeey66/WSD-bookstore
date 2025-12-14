package kr.ac.jbnu.ksh.wsdbookstoreassign2.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record UserCreateRequest(
        @Email @NotBlank String email,
        @NotBlank @Size(min = 8, max = 72) String password,
        @NotBlank @Size(max = 100) String name,
        @NotBlank @Size(max = 20) String gender,
        LocalDate birthDate,
        @NotBlank String address,
        @NotBlank @Size(max = 30) String phoneNumber
) {}

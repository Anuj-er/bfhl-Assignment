package com.bajajfinserv.bfhl.config;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

/** Values returned with every response. Set them through environment variables before deployment. */
@Validated
@ConfigurationProperties(prefix = "app.profile")
public record UserProfileProperties(
        @NotBlank String fullName,
        @NotBlank @Pattern(regexp = "\\d{8}", message = "dateOfBirth must be in ddMMyyyy format") String dateOfBirth,
        @NotBlank @Email String email,
        @NotBlank String rollNumber) {
}

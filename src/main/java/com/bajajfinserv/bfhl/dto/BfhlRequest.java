package com.bajajfinserv.bfhl.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record BfhlRequest(@NotNull(message = "data is required")
                          List<@NotBlank(message = "data items cannot be blank") String> data) {
}

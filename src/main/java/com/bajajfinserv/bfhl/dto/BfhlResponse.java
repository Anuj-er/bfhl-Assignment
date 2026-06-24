package com.bajajfinserv.bfhl.dto;

import com.bajajfinserv.bfhl.config.UserProfileProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Locale;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record BfhlResponse(
        @JsonProperty("is_success") boolean isSuccess,
        @JsonProperty("user_id") String userId,
        String email,
        @JsonProperty("roll_number") String rollNumber,
        @JsonProperty("odd_numbers") List<String> oddNumbers,
        @JsonProperty("even_numbers") List<String> evenNumbers,
        List<String> alphabets,
        @JsonProperty("special_characters") List<String> specialCharacters,
        String sum,
        @JsonProperty("concat_string") String concatString,
        String error) {

    public static BfhlResponse success(UserProfileProperties profile, List<String> oddNumbers,
                                       List<String> evenNumbers, List<String> alphabets,
                                       List<String> specialCharacters, String sum, String concatString) {
        return new BfhlResponse(true, userId(profile), profile.email(), profile.rollNumber(), oddNumbers,
                evenNumbers, alphabets, specialCharacters, sum, concatString, null);
    }

    public static BfhlResponse failure(UserProfileProperties profile, String message) {
        return new BfhlResponse(false, userId(profile), profile.email(), profile.rollNumber(), List.of(),
                List.of(), List.of(), List.of(), "0", "", message);
    }

    private static String userId(UserProfileProperties profile) {
        String normalizedName = profile.fullName().trim().toLowerCase(Locale.ROOT).replaceAll("\\s+", "_");
        return normalizedName + "_" + profile.dateOfBirth();
    }
}

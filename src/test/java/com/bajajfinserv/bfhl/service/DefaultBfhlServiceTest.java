package com.bajajfinserv.bfhl.service;

import com.bajajfinserv.bfhl.config.UserProfileProperties;
import com.bajajfinserv.bfhl.dto.BfhlRequest;
import com.bajajfinserv.bfhl.dto.BfhlResponse;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class DefaultBfhlServiceTest {

    private final BfhlService service = new DefaultBfhlService(
            new UserProfileProperties("Jane Doe", "17091999", "jane@example.com", "ABCD123"));

    @Test
    void separatesMixedValuesAndBuildsTheRequiredResponse() {
        BfhlResponse response = service.process(new BfhlRequest(List.of("a", "1", "334", "4", "R", "$")));

        assertThat(response.isSuccess()).isTrue();
        assertThat(response.userId()).isEqualTo("jane_doe_17091999");
        assertThat(response.oddNumbers()).containsExactly("1");
        assertThat(response.evenNumbers()).containsExactly("334", "4");
        assertThat(response.alphabets()).containsExactly("A", "R");
        assertThat(response.specialCharacters()).containsExactly("$");
        assertThat(response.sum()).isEqualTo("339");
        assertThat(response.concatString()).isEqualTo("Ra");
    }

    @Test
    void reversesIndividualCharactersAcrossMultiLetterAlphabetTokens() {
        BfhlResponse response = service.process(new BfhlRequest(List.of("A", "ABCD", "DOE")));

        assertThat(response.alphabets()).containsExactly("A", "ABCD", "DOE");
        assertThat(response.concatString()).isEqualTo("EoDdCbAa");
    }

    @Test
    void preservesNumericStringsAndSupportsIntegersBeyondLongRange() {
        BfhlResponse response = service.process(new BfhlRequest(List.of("-3", "0002", "9223372036854775808")));

        assertThat(response.oddNumbers()).containsExactly("-3");
        assertThat(response.evenNumbers()).containsExactly("0002", "9223372036854775808");
        assertThat(response.sum()).isEqualTo("9223372036854775807");
    }
}

package com.bajajfinserv.bfhl.service;

import com.bajajfinserv.bfhl.config.UserProfileProperties;
import com.bajajfinserv.bfhl.dto.BfhlRequest;
import com.bajajfinserv.bfhl.dto.BfhlResponse;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

@Service
public class DefaultBfhlService implements BfhlService {

    private static final Pattern INTEGER = Pattern.compile("[+-]?\\d+");

    private final UserProfileProperties profile;

    public DefaultBfhlService(UserProfileProperties profile) {
        this.profile = profile;
    }

    @Override
    public BfhlResponse process(BfhlRequest request) {
        List<String> oddNumbers = new ArrayList<>();
        List<String> evenNumbers = new ArrayList<>();
        List<String> alphabets = new ArrayList<>();
        List<String> specialCharacters = new ArrayList<>();
        StringBuilder sourceLetters = new StringBuilder();
        BigInteger sum = BigInteger.ZERO;

        for (String value : request.data()) {
            if (INTEGER.matcher(value).matches()) {
                BigInteger number = new BigInteger(value);
                if (number.testBit(0)) {
                    oddNumbers.add(value);
                } else {
                    evenNumbers.add(value);
                }
                sum = sum.add(number);
            } else if (value.codePoints().allMatch(Character::isLetter)) {
                alphabets.add(value.toUpperCase(Locale.ROOT));
                sourceLetters.append(value);
            } else {
                specialCharacters.add(value);
            }
        }

        return BfhlResponse.success(profile, List.copyOf(oddNumbers), List.copyOf(evenNumbers),
                List.copyOf(alphabets), List.copyOf(specialCharacters), sum.toString(),
                alternatingCaseReverse(sourceLetters.toString()));
    }

    private String alternatingCaseReverse(String value) {
        int[] codePoints = value.codePoints().toArray();
        StringBuilder result = new StringBuilder(value.length());
        for (int sourceIndex = codePoints.length - 1, outputIndex = 0; sourceIndex >= 0; sourceIndex--, outputIndex++) {
            String character = new String(Character.toChars(codePoints[sourceIndex]));
            result.append(outputIndex % 2 == 0
                    ? character.toUpperCase(Locale.ROOT)
                    : character.toLowerCase(Locale.ROOT));
        }
        return result.toString();
    }
}

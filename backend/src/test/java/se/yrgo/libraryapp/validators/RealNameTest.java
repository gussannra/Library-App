package se.yrgo.libraryapp.validators;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RealNameTest {

    @ParameterizedTest
    @ValueSource(strings = {"bosse karlsson", "37-3!1i5a Gu5/4v_ 5s0n+"})
    void realNameValidName(String validName) {
        assertTrue(RealName.validate(validName));
    }

    @ParameterizedTest
    @ValueSource(strings = {"blimey", "darn", "frack", "bosse blimey bosse", "frack bosse", "bosse darn"})
    void realNameInvalidName(String badWord) {
        assertFalse(RealName.validate(badWord));
    }
}

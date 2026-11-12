package se.yrgo.libraryapp.validators;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RealNameTest {

    @ParameterizedTest
    @ValueSource(strings = {"Bosse Karlsson", "Lisa Lamm", "Kenneth Smith"})
    void realNameValidName(String validName) {
        assertTrue(RealName.validate(validName));
    }

    @ParameterizedTest
    @ValueSource(strings = {"blimey", "darn", "frack", "bosse blimey bosse", "frack bosse", "bosse darn"})
    void realNameInvalidName(String invalidName) {
        assertFalse(RealName.validate(invalidName));
    }
}

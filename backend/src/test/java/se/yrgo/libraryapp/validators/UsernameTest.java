package se.yrgo.libraryapp.validators;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class UsernameTest {

    @ParameterizedTest
    @ValueSource(strings = {"Lisa", "@bengt", "k.aj", "milo_", "KENNETH", "Maja-Lisa", "henrik0123456789", "@_-."})
    void checkValidUsername(String validUsername) {
        assertThat(Username.validate(validUsername)).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = {"user name", "kalle!?", "kaj", "Pöllö", "  ", "%&#"})
    @EmptySource
    void checkInvalidUsername(String invalidUsername) {
        assertThat(Username.validate(invalidUsername)).isFalse();
    }
}

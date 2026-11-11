package se.yrgo.libraryapp.validators;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class UtilsTest {

    @ParameterizedTest
    @ValueSource(strings = {"bosse karlsson", "BOSSE KARLSSON", "b1o2s!s%e karlsson"})
    void onlyLettersAndWhitespacesMixedInput(String realname) {
        assertThat(Utils.onlyLettersAndWhitespace(realname)).isEqualTo("bosse karlsson");
    }

    @ParameterizedTest
    @ValueSource(strings = {"k!a?l&le/", "12345k67890alle"})
    void onlyLettersAndWhitespaceReturnsOnlyLetters(String realname) {
        assertThat(Utils.onlyLettersAndWhitespace(realname)).isEqualTo("kalle");
    }

    @Test
    void onlyLettersAndWhitespaceKeepsWhitespaces() {
        assertThat(Utils.onlyLettersAndWhitespace(" bosse karl karlsson ")).isEqualTo(" bosse karl karlsson ");
    }

    @Test
    void onlylettersAndWhitespaceAcceptsEmptyString() {
        assertThat(Utils.onlyLettersAndWhitespace("")).isEqualTo("");
    }

    @ParameterizedTest
    @ValueSource(strings = {"BOSSE KARLSSON", "Bosse Karlsson"})
    void onlyLettersAndWhitespaceReturnsLowercase(String realname) {
        assertThat(Utils.onlyLettersAndWhitespace(realname)).isEqualTo("bosse karlsson");
    }

    @ParameterizedTest
    @ValueSource(strings = {"hello world", "Hell(#0, w0r1?d-"})
    void cleanAndUnleetMixedInput(String realname) {
        assertThat(Utils.cleanAndUnLeet(realname)).isEqualTo("hello world");
    }

    @Test
    void cleanAndUnleetConvertsToLetters() {
        assertThat(Utils.cleanAndUnLeet("80553")).isEqualTo("bosse");
    }

    @Test
    void cleanAndUnleetRemovesNonAlphabetics() {
        assertThat(Utils.cleanAndUnLeet("!8o?5_5&e-")).isEqualTo("bosse");
    }

    @Test
    void cleanAndUnleetReturnsLowercase() {
        assertThat(Utils.cleanAndUnLeet("BOSSE")).isEqualTo("bosse");
    }

    @Test
    void cleanAndUnleetKeepsWhitespace() {
        assertThat(Utils.cleanAndUnLeet(" bosse karlsson ")).isEqualTo(" bosse karlsson ");
    }
}

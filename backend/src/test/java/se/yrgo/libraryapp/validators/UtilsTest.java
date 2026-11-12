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

    @ParameterizedTest
    @ValueSource(strings = {"BOSSE KARLSSON", "Bosse Karlsson"})
    void onlyLettersAndWhitespaceReturnsLowercase(String realname) {
        assertThat(Utils.onlyLettersAndWhitespace(realname)).isEqualTo("bosse karlsson");
    }

    @Test
    void onlyLettersAndWhiteSpaceHandlesTab() {
        assertThat(Utils.onlyLettersAndWhitespace("\t")).isEqualTo("\t");
    }

    @Test
    void onlyLettersAndWhiteSpaceHandlesNewLine() {
        assertThat(Utils.onlyLettersAndWhitespace("\n")).isEqualTo("\n");
    }

    @ParameterizedTest
    @ValueSource(strings = {"hello world", "Hell(#0, w0r1?d-"})
    void cleanAndUnleetMixedInput(String realname) {
        assertThat(Utils.cleanAndUnLeet(realname)).isEqualTo("hello world");
    }

    @Test
    void cleanAndUnleetConvertsToLetters() {
        assertThat(Utils.cleanAndUnLeet("01345678")).isEqualTo("oleasgtb");
    }
}

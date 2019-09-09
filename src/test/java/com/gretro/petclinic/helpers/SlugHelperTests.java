package com.gretro.petclinic.helpers;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class SlugHelperTests {
    @ParameterizedTest()
    @MethodSource("provideData")
    public void when_passing_valid_string_should_normalize_into_slug(List<String> inputs, String expected) {
        String actual = SlugHelper.calculateSlug(inputs.toArray(new String[0]));

        assertThat(actual).isEqualTo(expected);
    }

    static Stream<Arguments> provideData() {
        return Stream.of(
            Arguments.arguments(List.of("John", "Doe"), "john-doe"),
            Arguments.arguments(List.of("Éric Déhaies", "Gélinas"), "eric-dehaies-gelinas"),
            Arguments.arguments(List.of(" ", "  Bob",  "Dole ", "\n\r"), "bob-dole"),
            Arguments.arguments(List.of("\r\n", "Yùry", "_Gagarine"), "yury-gagarine"),
            Arguments.arguments(List.of("Vinçent", "Maraîchés"), "vincent-maraiches"),
            Arguments.arguments(List.of("]a[èàçé001! "), "aeace001")
        );
    }
}

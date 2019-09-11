package com.gretro.petclinic.helpers;

import com.gretro.petclinic.utils.SlugHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class SlugHelperTests {
    @ParameterizedTest()
    @MethodSource("provideData")
    public void when_passing_valid_string_should_normalize_into_slug(List<String> inputs, String expected) {
        var actual = SlugHelper.calculateSlug(inputs.toArray(new String[0]));

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

    @Test
    public void when_incrementing_slug_with_no_similar_slugs_should_return_tentative_slug() {
        // Arrange
        var tentativeSlug = "my-slug";

        // Act
        var finalSlug = SlugHelper.incrementSlug(tentativeSlug, new ArrayList<>());

        // Assert
        assertThat(finalSlug).isEqualTo(tentativeSlug);
    }

    @Test
    public void when_incrementing_slug_with_similar_but_different_slugs_should_return_tentative_slug() {
        // Arrange
        var tentativeSlug = "my-slug";
        var existingSlugs = List.of(
            "my-slug-is-cool"
        );

        // Act
        var finalSlug = SlugHelper.incrementSlug(tentativeSlug, existingSlugs);

        // Assert
        assertThat(finalSlug).isEqualTo(tentativeSlug);
    }

    @Test
    public void when_increment_slug_with_some_similar_slugs_should_return_incremented_slug() {
        // Arrange
        var tentativeSlug = "my-slug";
        var existingSlugs = List.of(
            SlugHelper.calculateSlug(tentativeSlug, Integer.toString(3)),
            SlugHelper.calculateSlug(tentativeSlug, Integer.toString(5)),
            SlugHelper.calculateSlug(tentativeSlug, Integer.toString(2))
        );

        // Act
        var finalSlug = SlugHelper.incrementSlug(tentativeSlug, existingSlugs);

        // Assert
        assertThat(finalSlug).isEqualTo("my-slug-6");
    }

    @Test
    public void when_increment_slug_with_big_indexed_slug_should_return_incremented_slug() {
        // Arrange
        var tentativeSlug = "my-slug";
        var existingSlugs = List.of(
            SlugHelper.calculateSlug(tentativeSlug, Integer.toString(304)),
            SlugHelper.calculateSlug(tentativeSlug, Integer.toString(513)),
            SlugHelper.calculateSlug(tentativeSlug, Integer.toString(2047))
        );

        // Act
        var finalSlug = SlugHelper.incrementSlug(tentativeSlug, existingSlugs);

        // Assert
        assertThat(finalSlug).isEqualTo("my-slug-2048");
    }

    @Test
    public void when_increment_slug_with_non_indexed_slug_should_return_incremented_slug() {
        // Arrange
        var tentativeSlug = "my-slug";
        var existingSlugs = List.of(tentativeSlug);

        // Act
        var finalSlug = SlugHelper.incrementSlug(tentativeSlug, existingSlugs);

        // Assert
        assertThat(finalSlug).isEqualTo("my-slug-2");
    }
}

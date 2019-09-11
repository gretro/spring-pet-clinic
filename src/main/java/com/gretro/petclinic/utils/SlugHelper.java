package com.gretro.petclinic.utils;

import com.google.common.base.Strings;
import com.google.common.primitives.Ints;
import org.springframework.util.StringUtils;

import java.text.Normalizer;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class SlugHelper {
    private final static String notAlphaNumRegex = "[^a-z0-9-]";

    public static String calculateSlug(String... fields) {
        String slug = Arrays.stream(fields)
            .map(SlugHelper::curateSlugField)
            .filter(part -> !StringUtils.isEmpty(part))
            .collect(Collectors.joining("-"));

        return slug;
    }

    public static String incrementSlug(String tentativeSlug, List<String> similarSlugs) {
        var maybeNextIndex = similarSlugs.stream()
                .map(slug -> slug.replace(tentativeSlug,""))
                .map(remainingSlug -> {
                    if (Strings.isNullOrEmpty(remainingSlug)) {
                        return Optional.of(1);
                    }

                    return Optional.ofNullable(Ints.tryParse(remainingSlug.substring(1)));
                })
                .filter(maybeIndex -> maybeIndex.isPresent())
                .map(maybeSlugIndex -> maybeSlugIndex.get() + 1)
                .max(Integer::compareTo);

        String finalSlug = maybeNextIndex.map(nextIndex -> String.format("%s-%d", tentativeSlug, nextIndex))
                .orElse(tentativeSlug);

        return finalSlug;
    }

    private static String curateSlugField(String field) {
        String result = field;
        result = result.toLowerCase();
        result = stripAccents(result);
        result = result.replace("_", " ");
        result = result.replaceAll("\\s", " ");
        result = result.trim();
        result = result.replace(" ", "-");
        result = result.replaceAll(notAlphaNumRegex, "");

        return result;
    }

    private static String stripAccents(String field) {
        String result = Normalizer.normalize(field, Normalizer.Form.NFD)
                .replaceAll("[^\\p{ASCII}]", "");

        return result;
    }
}

package com.gretro.petclinic.helpers;

import org.springframework.util.StringUtils;

import java.text.Normalizer;
import java.util.Arrays;
import java.util.stream.Collectors;

public abstract class SlugHelper {
    private final static String notAlphaNumRegex = "[^a-z0-9-]";

    // TODO: Unit test this helper
    public static String calculateSlug(String... fields) {
        String slug = Arrays.stream(fields)
            .map(SlugHelper::curateSlugField)
            .filter(part -> !StringUtils.isEmpty(part))
            .collect(Collectors.joining("-"));

        return slug;
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

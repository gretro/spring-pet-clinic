package com.gretro.petclinic.exceptions;

import lombok.Getter;

public class EntityNotFoundException extends RuntimeException {
    @Getter
    private final String type;

    @Getter
    private final Object id;

    public EntityNotFoundException(Class clazz, Object id) {
        this(clazz.getName(), id);
    }

    public EntityNotFoundException(String type, Object id) {
        super(String.format("Could not find entity {%s} with ID \"%s\"", type, id.toString()));

        this.type = type;
        this.id = id;
    }
}

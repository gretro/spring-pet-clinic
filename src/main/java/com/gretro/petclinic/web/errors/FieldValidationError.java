package com.gretro.petclinic.web.errors;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FieldValidationError {
    private String fieldName;
    private String code;
    private String message;
}

package com.gretro.petclinic.web.errors;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiValidationError {
    private Instant timestamp;
    private Map<String, FieldValidationError> fieldErrors = new HashMap<>();
}

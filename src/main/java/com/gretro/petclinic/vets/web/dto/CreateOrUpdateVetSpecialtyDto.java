package com.gretro.petclinic.vets.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrUpdateVetSpecialtyDto {
    @NotBlank
    private String name;
}

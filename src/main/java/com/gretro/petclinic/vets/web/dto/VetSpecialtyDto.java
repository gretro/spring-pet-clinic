package com.gretro.petclinic.vets.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VetSpecialtyDto {
    private String id;
    private String name;
}

package com.gretro.petclinic.vets.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VetSpecialtiesResponse {
    private List<VetSpecialtyDto> vetSpecialties;
}

package com.gretro.petclinic.vets.web.mappers;

import com.gretro.petclinic.vets.models.VetSpecialty;
import com.gretro.petclinic.vets.web.dto.CreateOrUpdateVetSpecialtyDto;
import com.gretro.petclinic.vets.web.dto.VetSpecialtyDto;
import org.springframework.stereotype.Service;

@Service
public class VetSpecialityMapper {
    public VetSpecialtyDto toDto(VetSpecialty model) {
        VetSpecialtyDto dto = VetSpecialtyDto.builder()
            .id(model.getSlug())
            .name(model.getName())
            .build();

        return dto;
    }

    public VetSpecialty toModel(VetSpecialtyDto dto) {
        VetSpecialty model = VetSpecialty.builder()
                .slug(dto.getId())
                .name(dto.getName())
                .build();

        return model;
    }

    public VetSpecialty toModel(CreateOrUpdateVetSpecialtyDto dto) {
        VetSpecialty model = VetSpecialty.builder()
            .name(dto.getName())
            .build();

        return model;
    }
}

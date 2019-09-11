package com.gretro.petclinic.vets.web;

import com.gretro.petclinic.exceptions.EntityNotFoundException;
import com.gretro.petclinic.vets.models.VetSpecialty;
import com.gretro.petclinic.vets.services.VetSpecialtyService;
import com.gretro.petclinic.vets.web.dto.CreateOrUpdateVetSpecialtyDto;
import com.gretro.petclinic.vets.web.dto.VetSpecialtiesResponse;
import com.gretro.petclinic.vets.web.dto.VetSpecialtyDto;
import com.gretro.petclinic.vets.web.mappers.VetSpecialityMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/vet-specialties")
@Api(value = "Vet Specialties", tags = "Vet Specialties")
public class VetSpecialtyController {
    private final VetSpecialtyService vetSpecialtyService;
    private final VetSpecialityMapper mapper;

    public VetSpecialtyController(VetSpecialtyService vetSpecialtiesService, VetSpecialityMapper mapper) {

        this.vetSpecialtyService = vetSpecialtiesService;
        this.mapper = mapper;
    }

    @GetMapping(path = "")
    @ApiOperation(value = "Get all vet specialities")
    public VetSpecialtiesResponse getAll() {
        var vetSpecialties = this.vetSpecialtyService.getAllVetSpecialties();

        var vetSpecialtyDtos = vetSpecialties.stream()
                .map(this.mapper::toDto)
                .collect(Collectors.toList());

        var response = VetSpecialtiesResponse.builder()
                .vetSpecialties(vetSpecialtyDtos)
                .build();

        return response;
    }

    @GetMapping(path = "{slug}")
    @ApiOperation(value = "Get vet specialty")
    public VetSpecialtyDto get(@PathVariable String slug) {
        var dto = this.vetSpecialtyService.findBySlug(slug)
            .map(this.mapper::toDto)
            .orElseThrow(() -> new EntityNotFoundException(VetSpecialty.class, slug));

        return dto;
    }

    @PostMapping(path = "")
    @ApiOperation(value = "Create vet specialty")
    @ResponseStatus(HttpStatus.CREATED)
    public VetSpecialtyDto create(@Valid @RequestBody CreateOrUpdateVetSpecialtyDto request) {
        var model = this.mapper.toModel(request);

        var savedModel = this.vetSpecialtyService.create(model);

        return this.mapper.toDto(savedModel);
    }

    @PutMapping(path = "{slug}")
    @ApiOperation(value = "Updates a Vet Specialty")
    public VetSpecialtyDto update(@PathVariable String slug, @Valid @RequestBody CreateOrUpdateVetSpecialtyDto request) {
        var model = this.mapper.toModel(request);

        var savedModel = this.vetSpecialtyService.update(slug, model);
        return this.mapper.toDto(savedModel);
    }
}

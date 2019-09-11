package com.gretro.petclinic.vets.web;

import com.gretro.petclinic.exceptions.EntityNotFoundException;
import com.gretro.petclinic.helpers.SlugHelper;
import com.gretro.petclinic.vets.models.VetSpecialty;
import com.gretro.petclinic.vets.repositories.VetSpecialtyRepository;
import com.gretro.petclinic.vets.web.dto.CreateVetSpecialtyDto;
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
    private final VetSpecialtyRepository vetSpecialtyRepository;
    private final VetSpecialityMapper mapper;

    public VetSpecialtyController(VetSpecialtyRepository vetSpecialtyRepository, VetSpecialityMapper mapper) {

        this.vetSpecialtyRepository = vetSpecialtyRepository;
        this.mapper = mapper;
    }

    @GetMapping(path = "")
    @ApiOperation(value = "Get all vet specialities", notes = "Get all vet specialities")
    public VetSpecialtiesResponse getAll() {
        var vetSpecialties = this.vetSpecialtyRepository.findAll();

        var vetSpecialtyDtos = vetSpecialties.stream()
                .map(this.mapper::toDto)
                .collect(Collectors.toList());

        var response = VetSpecialtiesResponse.builder()
                .vetSpecialties(vetSpecialtyDtos)
                .build();

        return response;
    }

    @GetMapping(path = "{slug}")
    @ApiOperation(value = "Get vet specialty", notes = "Get specific vet specialty")
    public VetSpecialtyDto get(@PathVariable String slug) {
        var dto = this.vetSpecialtyRepository.findBySlug(slug)
            .map(this.mapper::toDto)
            .orElseThrow(() -> new EntityNotFoundException(VetSpecialty.class, slug));

        return dto;
    }

    @PostMapping(path = "")
    @ApiOperation(value = "Create vet specialty")
    @ResponseStatus(HttpStatus.CREATED)
    public VetSpecialtyDto create(@Valid @RequestBody CreateVetSpecialtyDto request) {
        var model = this.mapper.toModel(request);

        // TODO: Replace by smarter service
        model.setSlug(SlugHelper.calculateSlug(model.getName()));

        // TODO: Replace with principal information
        model.setCreatedBy("unknown");

        var savedModel = this.vetSpecialtyRepository.save(model);

        return this.mapper.toDto(savedModel);
    }
}

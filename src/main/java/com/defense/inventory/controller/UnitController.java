package com.defense.inventory.controller;

import com.defense.inventory.dto.UnitRequestDto;
import com.defense.inventory.dto.UnitResponseDto;
import com.defense.inventory.service.UnitService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/units")
@Slf4j
@RequiredArgsConstructor
public class UnitController {

    private final UnitService unitService;

    @Operation(
            summary = "Creates a new Unit in the DB",
            description = "Adds a new unit in the DB"
    )
    @PostMapping
    public ResponseEntity<UnitResponseDto> createUnit(@Valid @RequestBody UnitRequestDto unitRequestDto) {
        log.info("Received request to create unit");
        return ResponseEntity.ok(unitService.createUnit(unitRequestDto));
    }

    @Operation(
            summary = "Return Unit by id",
            description = "Return unit details by id"
    )
    @GetMapping("/{id}")
    public ResponseEntity<UnitResponseDto> getUnitById(@PathVariable("id") Long unitId) {
        log.info("Fetching unit with ID: {}", unitId);
        return ResponseEntity.ok(unitService.getUnitById(unitId));
    }

    @Operation(
            summary = "Return All Units",
            description = "Return all unit details"
    )
    @GetMapping
    public ResponseEntity<List<UnitResponseDto>> getAllUnits() {
        log.info("Fetching all units");
        return ResponseEntity.ok(unitService.getAllUnits());
    }

    @Operation(
            summary = "Update Unit Details",
            description = "Update the unit details"
    )
    @PutMapping("/{id}")
    public ResponseEntity<UnitResponseDto> updateUnit(@PathVariable("id") Long unitId, @Valid @RequestBody UnitRequestDto updatedUnit) {
        log.info("Updating unit with ID: {}", unitId);
        return ResponseEntity.ok(unitService.updateUnit(unitId, updatedUnit));
    }

    @Operation(
            summary = "Delete Unit by id",
            description = "Delete unit details by id"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUnit(@PathVariable("id") Long unitId) {
        log.info("Deleting unit with ID: {}", unitId);
        return ResponseEntity.ok(unitService.deleteUnit(unitId));
    }
}

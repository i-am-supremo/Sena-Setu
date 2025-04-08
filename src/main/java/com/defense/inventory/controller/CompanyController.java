package com.defense.inventory.controller;

import com.defense.inventory.dto.CompanyRequestDto;
import com.defense.inventory.dto.CompanyResponseDto;
import com.defense.inventory.service.CompanyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies")
@Slf4j
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    @PostMapping("/unit/{unitId}")
    public ResponseEntity<CompanyResponseDto> createCompany(@Valid @RequestBody CompanyRequestDto companyRequestDto, @PathVariable Long unitId) {
        log.info("Received request to create company under unit ID: {}", unitId);
        return ResponseEntity.ok(companyService.createCompany(companyRequestDto, unitId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompanyResponseDto> getCompanyById(@PathVariable("id") Long companyId) {
        log.info("Fetching company with ID: {}", companyId);
        return ResponseEntity.ok(companyService.getCompanyById(companyId));
    }

    @GetMapping
    public ResponseEntity<List<CompanyResponseDto>> getAllCompanies() {
        log.info("Fetching all companies");
        return ResponseEntity.ok(companyService.getAllCompanies());
    }

    @PutMapping("/{id}/unit/{unitId}")
    public ResponseEntity<CompanyResponseDto> updateCompany(@PathVariable("id") Long companyId, @Valid @RequestBody CompanyRequestDto updatedCompany, @PathVariable Long unitId) {
        log.info("Updating company with ID: {}", companyId);
        return ResponseEntity.ok(companyService.updateCompany(companyId, updatedCompany, unitId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCompany(@PathVariable("id") Long companyId) {
        log.info("Deleting company with ID: {}", companyId);
        return ResponseEntity.ok(companyService.deleteCompany(companyId));
    }
}
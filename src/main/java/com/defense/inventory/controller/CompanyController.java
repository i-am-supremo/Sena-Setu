package com.defense.inventory.controller;

import com.defense.inventory.dto.CompanyRequestDto;
import com.defense.inventory.dto.CompanyResponseDto;
import com.defense.inventory.service.CompanyService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies")
@Slf4j
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    @Operation(
            summary = "Create a New Company, ADMIN Access Only",
            description = "This will Create a new company in the DB"
    )
    @PostMapping("/create/unit/{unitId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CompanyResponseDto> createCompany(@Valid @RequestBody CompanyRequestDto companyRequestDto, @PathVariable Long unitId) {
        log.info("Received request to create company under unit ID: {}", unitId);
        return ResponseEntity.ok(companyService.createCompany(companyRequestDto, unitId));
    }

    @Operation(
            summary = "Return Company Details By ID",
            description = "This will return the company detail by id"
    )
    @GetMapping("/get/{id}")
    public ResponseEntity<CompanyResponseDto> getCompanyById(@PathVariable("id") Long companyId) {
        log.info("Fetching company with ID: {}", companyId);
        return ResponseEntity.ok(companyService.getCompanyById(companyId));
    }

    @Operation(
            summary = "Fetch All Companies",
            description = "This will return all the companies created"
    )
    @GetMapping("/getAll")
    public ResponseEntity<List<CompanyResponseDto>> getAllCompanies() {
        log.info("Fetching all companies");
        return ResponseEntity.ok(companyService.getAllCompanies());
    }

    @Operation(
            summary = "Fetch List of Companies based on unit id",
            description = "This will return all the companies based on unit id"
    )
    @GetMapping("/by-unit/{unitId}")
    public ResponseEntity<List<CompanyResponseDto>> getCompanyByUnitId(@PathVariable Long unitId) {
        List<CompanyResponseDto> companies = companyService.getCompanyByUnitId(unitId);
        return ResponseEntity.ok(companies);
    }

    @Operation(
            summary = "Updates the Details of the existing company, ADMIN Access Only",
            description = "This will update the detail of existing company"
    )
    @PutMapping("/update/{id}/unit/{unitId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CompanyResponseDto> updateCompany(@PathVariable("id") Long companyId, @Valid @RequestBody CompanyRequestDto updatedCompany, @PathVariable Long unitId) {
        log.info("Updating company with ID: {}", companyId);
        return ResponseEntity.ok(companyService.updateCompany(companyId, updatedCompany, unitId));
    }

    @Operation(
            summary = "Delete the Details of the existing company, ADMIN Access Only",
            description = "This will delete existing company"
    )
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteCompany(@PathVariable("id") Long companyId) {
        log.info("Deleting company with ID: {}", companyId);
        return ResponseEntity.ok(companyService.deleteCompany(companyId));
    }
}
package com.defense.inventory.service;

import com.defense.inventory.dto.CompanyRequestDto;
import com.defense.inventory.dto.CompanyResponseDto;

import java.util.List;

public interface CompanyService {

    CompanyResponseDto createCompany(CompanyRequestDto companyRequestDto, Long unitId);

    CompanyResponseDto getCompanyById(Long companyId);

    List<CompanyResponseDto> getAllCompanies();

    CompanyResponseDto updateCompany(Long companyId, CompanyRequestDto updatedCompany, Long unitId);

    String deleteCompany(Long companyId);

    List<CompanyResponseDto> getCompanyByUnitId(Long unitId);
}

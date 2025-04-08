package com.defense.inventory.service.impl;

import com.defense.inventory.dto.CompanyRequestDto;
import com.defense.inventory.dto.CompanyResponseDto;
import com.defense.inventory.entity.Company;
import com.defense.inventory.entity.Unit;
import com.defense.inventory.exception.ResourceNotFoundException;
import com.defense.inventory.repository.CompanyRepository;
import com.defense.inventory.repository.UnitRepository;
import com.defense.inventory.service.CompanyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final UnitRepository unitRepository;
    private final ModelMapper modelMapper;

    @Override
    public CompanyResponseDto createCompany(CompanyRequestDto companyRequestDto, Long unitId) {
        Unit unit = unitRepository.findById(unitId).orElseThrow(() -> new ResourceNotFoundException("No Unit ", "id ", unitId));
        Company company = modelMapper.map(companyRequestDto, Company.class);
        company.setUnit(unit);
        return modelMapper.map(companyRepository.save(company), CompanyResponseDto.class);
    }

    @Override
    public CompanyResponseDto getCompanyById(Long companyId) {
        log.info("Getting company details by Id");
        return modelMapper.map(companyRepository.findById(companyId).orElseThrow(() -> new ResourceNotFoundException("No company ", "id ", companyId)), CompanyResponseDto.class);
    }

    @Override
    public List<CompanyResponseDto> getAllCompanies() {
        log.info("Getting all unit details");
        return companyRepository.findAll()
                .stream()
                .map(company -> modelMapper.map(company, CompanyResponseDto.class))
                .toList();
    }

    @Override
    public CompanyResponseDto updateCompany(Long companyId, CompanyRequestDto updatedCompany, Long unitId) {
        Company company = companyRepository.findById(companyId).orElseThrow(() -> new ResourceNotFoundException("No Company ", "id ", companyId));
        company.setName(updatedCompany.getName());
        company.setDescription(updatedCompany.getDescription());
        if(company.getUnit().getId() != unitId)
        {
            Unit unit = unitRepository.findById(unitId).orElseThrow(() -> new ResourceNotFoundException("No Unit ", "id ", unitId));
            company.setUnit(unit);
        }
        return modelMapper.map(companyRepository.save(company), CompanyResponseDto.class);
    }

    @Override
    public String deleteCompany(Long companyId) {
        Company company = companyRepository.findById(companyId).orElseThrow(() -> new ResourceNotFoundException("No Company ", "id ", companyId));
        log.info("Deleting Company {}", company.getName());
        companyRepository.delete(company);
        return "Company Deleted Successfully";
    }
}

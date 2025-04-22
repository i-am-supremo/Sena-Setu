package com.defense.inventory.service.impl;

import com.defense.inventory.dto.CompanyRequestDto;
import com.defense.inventory.dto.CompanyResponseDto;
import com.defense.inventory.entity.Company;
import com.defense.inventory.entity.Unit;
import com.defense.inventory.exception.ResourceAlreadyExistException;
import com.defense.inventory.exception.ResourceNotFoundException;
import com.defense.inventory.repository.CompanyRepository;
import com.defense.inventory.repository.UnitRepository;
import com.defense.inventory.service.CompanyService;
import com.defense.inventory.utils.AppConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final UnitRepository unitRepository;
    private final ModelMapper modelMapper;
    private final LoggerServiceImpl loggerService;

    @Override
    public CompanyResponseDto createCompany(CompanyRequestDto companyRequestDto, Long unitId) {
        Unit unit = unitRepository.findById(unitId).orElseThrow(() -> new ResourceNotFoundException("No Unit ", "id ", unitId));
        Company company = modelMapper.map(companyRequestDto, Company.class);
        company.setName(companyRequestDto.getName().trim());
        company.setUnit(unit);
        
        Company alreadyExist = companyRepository.existsByNameIgnoreCaseAndUnitId(company.getName(), unitId);
        if (alreadyExist!=null) {
            throw new ResourceAlreadyExistException(company.getName() + " Already ", "This Unit ", unit.getName());
        }
        loggerService.saveLoggingDetails(AppConstants.CREATED_COMPANY, company.getName());
        Company savedCompany = companyRepository.save(company);
        CompanyResponseDto companyResponseDto = modelMapper.map(savedCompany, CompanyResponseDto.class);
        companyResponseDto.setUnitId(unit.getId());
        companyResponseDto.setUnitName(unit.getName());
        return companyResponseDto;
    }

    @Override
    public CompanyResponseDto getCompanyById(Long companyId) {
        log.info("Getting company details by Id");
        Company company = companyRepository.findById(companyId).orElseThrow(() -> new ResourceNotFoundException("No company ", "id ", companyId));
        CompanyResponseDto companyResponseDto = modelMapper.map(company, CompanyResponseDto.class);
        companyResponseDto.setUnitId(company.getUnit().getId());
        companyResponseDto.setUnitName(company.getUnit().getName());
        return companyResponseDto;
    }

    @Override
    public List<CompanyResponseDto> getAllCompanies() {
        log.info("Getting all unit details");
        List<Company> companyList = companyRepository.findAll();
        List<CompanyResponseDto> companyResponseDtos = new ArrayList<>();

        for (Company company : companyList) {
            CompanyResponseDto companyResponseDto = modelMapper.map(company, CompanyResponseDto.class);
            companyResponseDto.setUnitId(company.getUnit().getId());
            companyResponseDto.setUnitName(company.getUnit().getName());
            companyResponseDtos.add(companyResponseDto);
        }
        return companyResponseDtos;
    }

    @Override
    public CompanyResponseDto updateCompany(Long companyId, CompanyRequestDto updatedCompany, Long unitId) {
        Company company = companyRepository.findById(companyId).orElseThrow(() -> new ResourceNotFoundException("No Company ", "id ", companyId));
        company.setName(updatedCompany.getName().trim());
        company.setDescription(updatedCompany.getDescription());

        Unit unit = unitRepository.findById(unitId).orElseThrow(() -> new ResourceNotFoundException("No Unit ", "id ", unitId));
        company.setUnit(unit);

        Company alreadyExist = companyRepository.existsByNameIgnoreCaseAndUnitId(company.getName(), unitId);
        if (alreadyExist!=null && alreadyExist.getId() != companyId) {
            throw new ResourceAlreadyExistException(updatedCompany.getName() + " Already ", "This Unit ", unit.getName());
        }

        loggerService.saveLoggingDetails(AppConstants.UPDATED_COMPANY, company.getName());
        Company savedCompany = companyRepository.save(company);
        CompanyResponseDto companyResponseDto = modelMapper.map(savedCompany, CompanyResponseDto.class);
        companyResponseDto.setUnitId(unit.getId());
        companyResponseDto.setUnitName(unit.getName());

        return companyResponseDto;
    }

    @Override
    public String deleteCompany(Long companyId) {
        Company company = companyRepository.findById(companyId).orElseThrow(() -> new ResourceNotFoundException("No Company ", "id ", companyId));
        log.info("Deleting Company {}", company.getName());
        loggerService.saveLoggingDetails(AppConstants.DELETED_COMPANY, company.getName());
        companyRepository.delete(company);
        return "Company Deleted Successfully";
    }

    @Override
    public List<CompanyResponseDto> getCompanyByUnitId(Long unitId) {
        List<Company> companyList = companyRepository.findByUnitId(unitId);
        return companyList.stream()
                .map(company -> modelMapper.map(company, CompanyResponseDto.class))
                .collect(Collectors.toList());
    }
}

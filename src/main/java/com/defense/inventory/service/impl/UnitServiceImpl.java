package com.defense.inventory.service.impl;

import com.defense.inventory.dto.UnitRequestDto;
import com.defense.inventory.dto.UnitResponseDto;
import com.defense.inventory.entity.Unit;
import com.defense.inventory.exception.ResourceAlreadyExistException;
import com.defense.inventory.exception.ResourceNotFoundException;
import com.defense.inventory.repository.UnitRepository;
import com.defense.inventory.service.UnitService;
import com.defense.inventory.utils.AppConstants;
import com.defense.inventory.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UnitServiceImpl implements UnitService {

    private final UnitRepository unitRepository;
    private final ModelMapper modelMapper;
    private final LoggerServiceImpl loggerService;

    @Override
    public UnitResponseDto createUnit(UnitRequestDto unitRequestDto) {
        log.info("Registering New Unit...");
        Unit unit = modelMapper.map(unitRequestDto, Unit.class);
        Unit alreadyExist = unitRepository.findByName(unit.getName());
        if (alreadyExist != null)
            throw new ResourceAlreadyExistException("Unit already ", "Name ", unit.getName());
        log.info("Saving unit ...");
        loggerService.saveLoggingDetails(AppConstants.CREATED_UNIT, unit.getName());

        return modelMapper.map(unitRepository.save(unit), UnitResponseDto.class);
    }

    @Override
    public UnitResponseDto getUnitById(Long unitId) {
        log.info("Getting unit details by Id");
        return modelMapper.map(unitRepository.findById(unitId).orElseThrow(() -> new ResourceNotFoundException("No unit ", "id ", unitId)), UnitResponseDto.class);
    }

    @Override
    public List<UnitResponseDto> getAllUnits() {
        log.info("Getting all unit details");
        return unitRepository.findAll()
                .stream()
                .map(unit -> modelMapper.map(unit, UnitResponseDto.class))
                .toList();
    }

    @Override
    public UnitResponseDto updateUnit(Long unitId, UnitRequestDto updatedUnit) {
        Unit unit = unitRepository.findById(unitId).orElseThrow(() -> new ResourceNotFoundException("No unit ", "id ", unitId));
        unit.setName(updatedUnit.getName());
        unit.setDescription(updatedUnit.getDescription());
        Unit alreadyExist = unitRepository.findByName(updatedUnit.getName());
        if (alreadyExist != null)
            throw new ResourceAlreadyExistException("Unit already ", "Name ", unit.getName());
        log.info("Updating unit details of {}", unit.getName());
        loggerService.saveLoggingDetails(AppConstants.UPDATED_UNIT, unit.getName());
        return modelMapper.map(unitRepository.save(unit), UnitResponseDto.class);
    }

    @Override
    public String deleteUnit(Long unitId) {
        Unit unit = unitRepository.findById(unitId).orElseThrow(() -> new ResourceNotFoundException("No unit ", "id ", unitId));
        log.info("Deleting unit {}", unit.getName());
        loggerService.saveLoggingDetails(AppConstants.DELETED_UNIT, unit.getName());
        unitRepository.delete(unit);
        return "Unit Deleted Successfully";
    }
}

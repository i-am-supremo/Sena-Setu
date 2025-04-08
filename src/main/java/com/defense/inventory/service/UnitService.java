package com.defense.inventory.service;

import com.defense.inventory.dto.UnitRequestDto;
import com.defense.inventory.dto.UnitResponseDto;

import java.util.List;

public interface UnitService {

    UnitResponseDto createUnit(UnitRequestDto unit);

    UnitResponseDto getUnitById(Long unitId);

    List<UnitResponseDto> getAllUnits();

    UnitResponseDto updateUnit(Long unitId, UnitRequestDto updatedUnit);

    String deleteUnit(Long unitId);
}

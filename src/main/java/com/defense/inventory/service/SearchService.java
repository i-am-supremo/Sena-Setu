package com.defense.inventory.service;

import com.defense.inventory.dto.SearchResponseDto;

import java.util.List;

public interface SearchService {

    List<SearchResponseDto> search(String name);
}

package com.defense.inventory.controller;

import com.defense.inventory.dto.SearchResponseDto;
import com.defense.inventory.service.SearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/search")
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;

    @GetMapping("/{name}")
    public ResponseEntity<List<SearchResponseDto>> search(@PathVariable String name) {
        log.info("Searching the DB for {}", name);
        return ResponseEntity.ok(searchService.search(name));
    }

}

package com.defense.inventory.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PagedResponseDto<T> {
    private List<T> content;
    private int currentPage;
    private int totalPages;
    private long totalItems;
    private boolean isLast;
}

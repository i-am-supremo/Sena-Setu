package com.defense.inventory.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LoggerResponse {
    private Long id;
    private String detail;
    private LocalDateTime updatedAt;
}

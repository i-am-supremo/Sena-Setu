package com.defense.inventory.controller;

import com.defense.inventory.dto.LoggerResponse;
import com.defense.inventory.service.impl.LoggerServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/logger")
@Slf4j
@RequiredArgsConstructor
public class LoggerController {

    private final LoggerServiceImpl loggerService;

    @Operation(
            summary = "Returns the Logging details of the Project",
            description = "This will return the logging details of the project"
    )
    @GetMapping("/getLoggingDetails")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<LoggerResponse>> getLoggingDetails(@RequestParam(defaultValue = "0") int page)
    {
        return ResponseEntity.ok(loggerService.getLoggingDetails(page));
    }
}

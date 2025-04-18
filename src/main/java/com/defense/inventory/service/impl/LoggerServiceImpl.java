package com.defense.inventory.service.impl;

import com.defense.inventory.dto.LoggerResponse;
import com.defense.inventory.entity.Logger;
import com.defense.inventory.repository.LoggerRepository;
import com.defense.inventory.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class LoggerServiceImpl {

    private final JwtUtils jwtUtils;
    private final LoggerRepository loggerRepository;
    private final ModelMapper modelMapper;

    public void saveLoggingDetails(String action, String fieldName) {
        Claims claims = this.getClaims();
        Logger logger = new Logger();
        logger.setDetail(claims.get("name", String.class)+ " with Army Number "+claims.get("armyNumber", String.class)+ " having Rank "+claims.get("rank", String.class)+" "+ action+" "+ fieldName);
        logger.setUpdatedAt(LocalDateTime.now());
        loggerRepository.save(logger);
    }

    private Claims getClaims()
    {
        String token = jwtUtils.getCurrentRequestToken();
        return jwtUtils.getAllClaimsFromToken(token);
    }

    public List<LoggerResponse> getLoggingDetails(int page) {
        Pageable pageable = PageRequest.of(page, 50); // 50 records per page
        return loggerRepository.findAllByOrderByUpdatedAtDesc(pageable)
                .stream()
                .map(logger -> modelMapper.map(logger, LoggerResponse.class))
                .collect(Collectors.toList());
    }
}

package com.smartamal.device.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartamal.device.dto.DashboardDTO;
import com.smartamal.device.dto.StatisticsDTO;
import com.smartamal.device.service.StatisticsService;

import lombok.RequiredArgsConstructor;

/**
 * Controller untuk Dashboard dan Statistik
 * Smart Amal.
 */
@RestController
@RequestMapping("/api/device")
@RequiredArgsConstructor
@CrossOrigin("*")
public class StatisticsController {

    private final StatisticsService statisticsService;

    /**
     * Dashboard Summary
     */
    @GetMapping("/dashboard")
    public ResponseEntity<DashboardDTO> getDashboard() {

        return ResponseEntity.ok(
                statisticsService.getDashboard());

    }

    /**
     * Statistik Donasi Harian
     */
    @GetMapping("/statistics")
    public ResponseEntity<List<StatisticsDTO>> getStatistics() {

        return ResponseEntity.ok(
                statisticsService.getDonationStatistics());

    }

}
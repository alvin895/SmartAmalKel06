package com.smartamal.device.service;

import java.sql.Date;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.smartamal.device.dto.DashboardDTO;
import com.smartamal.device.dto.StatisticsDTO;
import com.smartamal.device.repository.TransactionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StatisticsService {

    private final TransactionRepository transactionRepository;

    /**
     * Dashboard Summary
     */
    public DashboardDTO getDashboard() {

        Integer totalDonation =
                transactionRepository.getTotalDonation();

        Integer todayDonation =
                transactionRepository.getTodayDonation();

        Long totalTransaction =
                transactionRepository.countBy();

        return DashboardDTO.builder()
                .totalDonation(totalDonation)
                .todayDonation(todayDonation)
                .totalTransaction(totalTransaction)
                .deviceStatus("ONLINE")
                .securityStatus("SAFE")
                .build();
    }

    /**
     * Statistik Donasi Harian
     */
    public List<StatisticsDTO> getDonationStatistics() {

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("dd-MM");

        return transactionRepository
                .getDonationStatistics()
                .stream()
                .map(data -> {

                    Date sqlDate =
                            (Date) data[0];

                    Integer amount =
                            ((Number) data[1]).intValue();

                    return StatisticsDTO.builder()
                            .date(sqlDate
                                    .toLocalDate()
                                    .format(formatter))
                            .amount(amount)
                            .build();

                })
                .collect(Collectors.toList());

    }

}
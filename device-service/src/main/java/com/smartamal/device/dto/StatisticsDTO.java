package com.smartamal.device.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO untuk menampilkan data statistik
 * grafik donasi pada Dashboard Flutter.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StatisticsDTO {

    /**
     * Tanggal transaksi
     * Contoh:
     * 20-06
     * 21-06
     * 22-06
     */
    private String date;

    /**
     * Total nominal donasi pada tanggal tersebut
     */
    private Integer amount;

}
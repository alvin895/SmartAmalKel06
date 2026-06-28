package com.smartamal.device.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO untuk menampilkan data Dashboard
 * pada aplikasi Flutter Smart Amal.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardDTO {

    /**
     * Total seluruh nominal donasi
     */
    private Integer totalDonation;

    /**
     * Total nominal donasi hari ini
     */
    private Integer todayDonation;

    /**
     * Jumlah seluruh transaksi
     */
    private Long totalTransaction;

    /**
     * Status device (ONLINE / OFFLINE)
     */
    private String deviceStatus;

    /**
     * Status keamanan (SAFE / ALERT)
     */
    private String securityStatus;

}
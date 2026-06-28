package com.smartamal.device.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.smartamal.device.model.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    /**
     * Total seluruh donasi
     */
    @Query("SELECT COALESCE(SUM(t.nominal),0) FROM Transaction t")
    Integer getTotalDonation();

    /**
     * Jumlah transaksi
     */
    Long countBy();

    /**
     * Total donasi hari ini
     */
    @Query(value = """
        SELECT COALESCE(SUM(nominal),0)
        FROM transactions
        WHERE DATE(created_at)=CURDATE()
        """, nativeQuery = true)
    Integer getTodayDonation();

    /**
     * Statistik donasi harian
     */
    @Query(value = """
        SELECT DATE(created_at), SUM(nominal)
        FROM transactions
        GROUP BY DATE(created_at)
        ORDER BY DATE(created_at)
        """, nativeQuery = true)
    List<Object[]> getDonationStatistics();

}
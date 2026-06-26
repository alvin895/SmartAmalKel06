package com.smartamal.device.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.smartamal.device.model.Transaction;
import com.smartamal.device.repository.TransactionRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeviceService {

    private final TransactionRepository transactionRepository;

    private final NotificationHelper notificationHelper;

    // =====================================================
    // SIMPAN TRANSAKSI DONASI
    // =====================================================

    public Transaction save(Transaction transaction) {

        Transaction savedTransaction =
                transactionRepository.save(transaction);

        log.info(
                "Transaksi berhasil disimpan dengan ID : {}",
                savedTransaction.getId());

        notificationHelper.sendDonationNotification(
                savedTransaction.getNominal(),
                savedTransaction.getDeviceName());

        return savedTransaction;

    }

    // =====================================================
    // GET ALL
    // =====================================================

    public List<Transaction> getAll() {

        return transactionRepository.findAll();

    }

    // =====================================================
    // GET BY ID
    // =====================================================

    public Transaction getById(Long id) {

        return transactionRepository
                .findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Transaksi tidak ditemukan"));

    }

    // =====================================================
    // DELETE
    // =====================================================

    public void delete(Long id) {

        transactionRepository.deleteById(id);

    }

}
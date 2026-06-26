package com.smartamal.device.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartamal.device.model.Transaction;
import com.smartamal.device.service.DeviceService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/device")
@RequiredArgsConstructor
@CrossOrigin("*")
public class DeviceController {

    private final DeviceService deviceService;

    // =====================================================
    // CREATE TRANSACTION (POSTMAN / MQTT)
    // =====================================================

    @PostMapping
    public ResponseEntity<Transaction> createTransaction(
            @RequestBody Transaction transaction) {

        return ResponseEntity.ok(
                deviceService.save(transaction));

    }

    // =====================================================
    // GET ALL TRANSACTIONS
    // =====================================================

    @GetMapping
    public ResponseEntity<List<Transaction>> getAllTransactions() {

        return ResponseEntity.ok(
                deviceService.getAll());

    }

    // =====================================================
    // GET TRANSACTION BY ID
    // =====================================================

    @GetMapping("/{id}")
    public ResponseEntity<Transaction> getTransactionById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                deviceService.getById(id));

    }

    // =====================================================
    // DELETE TRANSACTION
    // =====================================================

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTransaction(
            @PathVariable Long id) {

        deviceService.delete(id);

        return ResponseEntity.ok(
                "Transaksi berhasil dihapus");

    }

}
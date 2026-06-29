package com.smartamal.accesscontrol.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.smartamal.accesscontrol.dto.AccessDTO;
import com.smartamal.accesscontrol.model.AccessLog;
import com.smartamal.accesscontrol.model.RFIDCard;
import com.smartamal.accesscontrol.mqtt.MqttPublisher;
import com.smartamal.accesscontrol.repository.AccessRepository;
import com.smartamal.accesscontrol.repository.RFIDCardRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccessService {

    private final AccessRepository accessRepository;

    private final RFIDCardRepository rfidCardRepository;

    private final NotificationHelper notificationHelper;

    private final MqttPublisher mqttPublisher;

    // =====================================================
    // CREATE RFID CARD
    // =====================================================

    public RFIDCard createCard(RFIDCard card) {

        return rfidCardRepository.save(card);

    }

    // =====================================================
    // GET ALL RFID
    // =====================================================

    public List<RFIDCard> getAllCards() {

        return rfidCardRepository.findAll();

    }

    // =====================================================
    // GET RFID BY ID
    // =====================================================

    public RFIDCard getCardById(Long id) {

        return rfidCardRepository.findById(id)

                .orElseThrow(() ->

                        new RuntimeException(
                                "RFID Card tidak ditemukan"));

    }

    // =====================================================
    // UPDATE RFID
    // =====================================================

    public RFIDCard updateCard(
            Long id,
            RFIDCard request) {

        RFIDCard card = getCardById(id);

        card.setUid(request.getUid());

        card.setOwnerName(request.getOwnerName());

        card.setActive(request.getActive());

        return rfidCardRepository.save(card);

    }

    // =====================================================
    // DELETE RFID
    // =====================================================

    public void deleteCard(Long id) {

        RFIDCard card = getCardById(id);

        rfidCardRepository.delete(card);

    }

    // =====================================================
    // OPEN BOX FROM FLUTTER
    // =====================================================

    public AccessDTO openBox(AccessDTO request) {

        request.setCommand("OPEN");

        mqttPublisher.publishCommand(request);

        request.setStatus("SUCCESS");

        request.setMessage("Kotak Amal berhasil dibuka.");

        return request;
    }

    // =====================================================
    // CLOSE BOX FROM FLUTTER
    // =====================================================

    public AccessDTO closeBox(AccessDTO request) {

        request.setCommand("CLOSE");

        mqttPublisher.publishCommand(request);

        request.setStatus("SUCCESS");

        request.setMessage("Kotak Amal berhasil ditutup.");

        return request;
    }

    // =====================================================
    // CHECK RFID
    // =====================================================

    public AccessLog checkAccess(String uid) {

        RFIDCard card = rfidCardRepository

                .findByUid(uid)

                .orElseThrow(() ->

                        new RuntimeException(
                                "RFID Card tidak ditemukan"));

        AccessLog accessLog = AccessLog.builder()

                .uid(card.getUid())

                .ownerName(card.getOwnerName())

                .eventType("ACCESS")

                .status(card.getActive()
                        ? "GRANTED"
                        : "DENIED")

                .build();

        AccessLog saved = accessRepository.save(accessLog);

        notificationHelper.sendNotification(saved);

        return saved;

    }

    // =====================================================
    // SENSOR GETAR
    // =====================================================

    public AccessLog saveSecurityEvent() {

        AccessLog accessLog = AccessLog.builder()

                .uid("-")

                .ownerName("SYSTEM")

                .eventType("SECURITY")

                .status("WARNING")

                .build();

        AccessLog saved = accessRepository.save(accessLog);

        notificationHelper.sendNotification(saved);

        return saved;

    }

    // =====================================================
    // SAVE ACCESS LOG
    // =====================================================

    public AccessLog save(AccessLog accessLog) {

        AccessLog saved = accessRepository.save(accessLog);

        notificationHelper.sendNotification(saved);

        return saved;

    }

    // =====================================================
    // GET ALL LOG
    // =====================================================

    public List<AccessLog> getAll() {

        return accessRepository.findAll();

    }

    // =====================================================
    // GET LOG BY ID
    // =====================================================

    public AccessLog getById(Long id) {

        return accessRepository.findById(id)

                .orElseThrow(() ->

                        new RuntimeException(
                                "Access Log tidak ditemukan"));

    }

    // =====================================================
    // DELETE LOG
    // =====================================================

    public void delete(Long id) {

        accessRepository.deleteById(id);

    }

}
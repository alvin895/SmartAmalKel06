package com.smartamal.device.mqtt;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartamal.device.model.Transaction;
import com.smartamal.device.service.DeviceService;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class DonationListener {

    @Value("${mqtt.broker}")
    private String broker;

    @Value("${mqtt.client-id}")
    private String clientId;

    @Value("${mqtt.topic.donation}")
    private String topic;

    private final DeviceService deviceService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @PostConstruct
    public void init() {

        try {

            MqttClient client =
                    new MqttClient(
                            broker,
                            clientId + "-donation");

            MqttConnectOptions options =
                    new MqttConnectOptions();

            options.setAutomaticReconnect(true);

            options.setCleanSession(true);

            client.connect(options);

            client.subscribe(topic, (t, message) -> {

                try {

                    String payload =
                            new String(message.getPayload());

                    Transaction transaction =
                            objectMapper.readValue(
                                    payload,
                                    Transaction.class);

                    deviceService.save(transaction);

                    log.info(
                            "Donasi MQTT diterima : {}",
                            transaction.getNominal());

                } catch (Exception e) {

                    log.error(
                            "Gagal parsing donation MQTT : {}",
                            e.getMessage());

                }

            });

            log.info(
                    "DonationListener aktif pada topic {}",
                    topic);

        } catch (Exception e) {

            log.error(
                    "MQTT Donation gagal connect : {}",
                    e.getMessage());

        }

    }

}
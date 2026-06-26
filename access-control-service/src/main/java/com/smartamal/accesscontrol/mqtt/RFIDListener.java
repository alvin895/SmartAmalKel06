package com.smartamal.accesscontrol.mqtt;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartamal.accesscontrol.service.AccessService;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class RFIDListener {

    @Value("${mqtt.broker}")
    private String broker;

    @Value("${mqtt.client-id}")
    private String clientId;

    @Value("${mqtt.topic.rfid}")
    private String topic;

    private final AccessService accessService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @PostConstruct
    public void init() {

        try {

            MqttClient client =
                    new MqttClient(
                            broker,
                            clientId + "-rfid");

            MqttConnectOptions options =
                    new MqttConnectOptions();

            options.setAutomaticReconnect(true);

            options.setCleanSession(true);

            client.connect(options);

            client.subscribe(topic, (t, message) -> {

                try {

                    String payload =
                            new String(message.getPayload());

                    JsonNode json =
                            objectMapper.readTree(payload);

                    String uid =
                            json.get("uid").asText();

                    accessService.checkAccess(uid);

                    log.info(
                            "RFID MQTT diterima : {}",
                            uid);

                } catch (Exception e) {

                    log.error(
                            "Gagal parsing RFID MQTT : {}",
                            e.getMessage());

                }

            });

            log.info(
                    "RFIDListener aktif pada topic {}",
                    topic);

        } catch (Exception e) {

            log.error(
                    "MQTT RFID gagal connect : {}",
                    e.getMessage());

        }

    }

}
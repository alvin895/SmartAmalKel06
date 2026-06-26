package com.smartamal.device.mqtt;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartamal.device.service.NotificationHelper;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class DeviceStatusListener {

    @Value("${mqtt.broker}")
    private String broker;

    @Value("${mqtt.client-id}")
    private String clientId;

    @Value("${mqtt.topic.status}")
    private String topic;

    private final NotificationHelper notificationHelper;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @PostConstruct
    public void init() {

        try {

            MqttClient client =
                    new MqttClient(
                            broker,
                            clientId + "-status");

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

                    String deviceId =
                            json.get("deviceId").asText();

                    String status =
                            json.get("status").asText();

                    notificationHelper
                            .sendDeviceStatusNotification(
                                    deviceId,
                                    status);

                    log.info(
                            "Status Device [{}] : {}",
                            deviceId,
                            status);

                } catch (Exception e) {

                    log.error(
                            "Gagal parsing status MQTT : {}",
                            e.getMessage());

                }

            });

            log.info(
                    "DeviceStatusListener aktif pada topic {}",
                    topic);

        } catch (Exception e) {

            log.error(
                    "MQTT Status gagal connect : {}",
                    e.getMessage());

        }

    }

}
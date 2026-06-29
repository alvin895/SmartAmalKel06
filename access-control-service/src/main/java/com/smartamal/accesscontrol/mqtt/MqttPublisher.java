package com.smartamal.accesscontrol.mqtt;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartamal.accesscontrol.dto.AccessDTO;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class MqttPublisher {

    @Value("${mqtt.broker}")
    private String broker;

    @Value("${mqtt.client-id}")
    private String clientId;

    @Value("${mqtt.topic.access}")
    private String topic;

    private MqttClient client;

    private final ObjectMapper objectMapper =
            new ObjectMapper();

    @PostConstruct
    public void init() {

        try {

            client = new MqttClient(
                    broker,
                    clientId + "-publisher");

            MqttConnectOptions options =
                    new MqttConnectOptions();

            options.setAutomaticReconnect(true);
            options.setCleanSession(true);

            client.connect(options);

            log.info(
                    "MQTT Publisher berhasil terkoneksi.");

        } catch (Exception e) {

            log.error(
                    "MQTT Publisher gagal connect : {}",
                    e.getMessage());

        }

    }

    // =====================================================
    // PUBLISH COMMAND KE ESP32
    // =====================================================

    public void publishCommand(
            AccessDTO request) {

        try {

            String json =
                    objectMapper.writeValueAsString(
                            request);

            MqttMessage message =
                    new MqttMessage(
                            json.getBytes());

            message.setQos(1);

            client.publish(
                    topic,
                    message);

            log.info(
                    "Command MQTT berhasil dikirim : {}",
                    json);

        } catch (Exception e) {

            log.error(
                    "Gagal publish MQTT : {}",
                    e.getMessage());

        }

    }

}
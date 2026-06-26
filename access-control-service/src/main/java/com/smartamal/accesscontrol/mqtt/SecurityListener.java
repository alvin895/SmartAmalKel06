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
public class SecurityListener {

    @Value("${mqtt.broker}")
    private String broker;

    @Value("${mqtt.client-id}")
    private String clientId;

    @Value("${mqtt.topic.security}")
    private String topic;

    private final AccessService accessService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @PostConstruct
    public void init() {

        try {

            MqttClient client =
                    new MqttClient(
                            broker,
                            clientId + "-security");

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

                    String sensor =
                            json.get("sensor").asText();

                    String alarm =
                            json.get("alarm").asText();

                    accessService.saveSecurityEvent();

                    log.warn(
                            "Security MQTT [{}] {} : {}",
                            deviceId,
                            sensor,
                            alarm);

                } catch (Exception e) {

                    log.error(
                            "Gagal parsing Security MQTT : {}",
                            e.getMessage());

                }

            });

            log.info(
                    "SecurityListener aktif pada topic {}",
                    topic);

        } catch (Exception e) {

            log.error(
                    "MQTT Security gagal connect : {}",
                    e.getMessage());

        }

    }

}
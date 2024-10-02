package ru.t1.java.demo.kafka.producers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
public class KafkaClientProducer {

    private final KafkaTemplate kafkaTemplate;

    public void send(Long id) {
        try {
            kafkaTemplate.sendDefault(UUID.randomUUID().toString(), id).get();
            kafkaTemplate.flush();
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
    }


    public void sendTo(String topic, Object o) {
        try {
            kafkaTemplate.send(topic, o).get();
            kafkaTemplate.flush();
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
    }

}

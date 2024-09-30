package ru.t1.java.demo.kafka.producers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@RequiredArgsConstructor
@Component
public class KafkaDefaultProducer {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendTo(String topic, Object o) {
        try {
            kafkaTemplate.send(topic, o).get();
            kafkaTemplate.flush();
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
    }

    public void throwTraceToTopic(long executionTime, String topic, String methodName, Object[] args) {
        StringBuilder message = new StringBuilder();
        message.append("Method: ")
                .append(methodName)
                .append(", Execution Time: ")
                .append(executionTime)
                .append(" ms");
        if (args != null && args.length > 0) {
            message.append(", Arguments: ").append(Arrays.toString(args));
        }
        sendTo(topic, message.toString());
    }
}

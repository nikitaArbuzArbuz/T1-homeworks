package ru.t1.java.demo.kafka.producers;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Component
public class KafkaErrorTraceProducer {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${t1.kafka.topic.t1_demo_error_trace}")
    private String errorTraceTopic;

    public void sendErrorTrace(String methodName, Object[] params, String stackTrace) {
        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("methodName", methodName);
        errorDetails.put("params", params);
        errorDetails.put("stackTrace", stackTrace);

        kafkaTemplate.send(errorTraceTopic, errorDetails);
    }
}

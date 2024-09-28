package ru.t1.java.demo.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.t1.java.demo.aop.HandlingResult;
import ru.t1.java.demo.aop.LogException;
import ru.t1.java.demo.aop.Track;
import ru.t1.java.demo.kafka.producers.KafkaDefaultProducer;
import ru.t1.java.demo.model.dto.TransactionDto;
import ru.t1.java.demo.util.JsonParser;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class TransactionController {
    private final KafkaDefaultProducer kafkaProducer;
    @Value("${t1.kafka.topic.t1_demo_transactions}")
    private String topic;

    @LogException
    @Track
    @GetMapping(value = "/transaction/parse")
    @HandlingResult

    public void parseSource() {
        List<TransactionDto> transactionDataDtos = JsonParser.parseJsonData("TRANSACTION_DATA", TransactionDto[].class);
        transactionDataDtos.forEach(dto -> kafkaProducer.sendTo(topic, dto));
    }
}

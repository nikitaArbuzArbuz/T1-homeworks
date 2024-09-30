package ru.t1.java.demo.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.t1.java.demo.aop.HandlingResult;
import ru.t1.java.demo.model.dto.TransactionDto;
import ru.t1.java.demo.service.TransactionService;
import ru.t1.java.demo.util.JsonParser;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class TransactionController {
    private static final String TRANSACTION_DATA = "TRANSACTION_DATA";
    private final TransactionService transactionService;
    @Value("${t1.kafka.topic.t1_demo_transactions}")
    private String topic;


    @GetMapping(value = "/transaction/parse")
    @HandlingResult
    public void parseSource() {
        List<TransactionDto> transactionDataDtos = JsonParser.parseJsonData(TRANSACTION_DATA, TransactionDto[].class);
        transactionService.sendToProducer(transactionDataDtos, topic);
        try {
            // для отработки ErrorTraceAspect
            throw new RuntimeException("Exception!");
        } catch (Exception e) {
            System.out.println("Exception caught: " + e.getMessage());
            throw e;
        }
    }
}

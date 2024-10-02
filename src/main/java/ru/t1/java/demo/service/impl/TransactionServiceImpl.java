package ru.t1.java.demo.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.t1.java.demo.kafka.producers.KafkaDefaultProducer;
import ru.t1.java.demo.model.Transaction;
import ru.t1.java.demo.model.dto.TransactionDto;
import ru.t1.java.demo.repository.TransactionRepository;
import ru.t1.java.demo.service.TransactionService;

import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class TransactionServiceImpl implements TransactionService {
    private final KafkaDefaultProducer kafkaProducer;
    private final TransactionRepository repository;

    @Override
    @Transactional
    public void saveTransaction(List<Transaction> transactions) {
        transactions.forEach(repository::saveAndFlush);
    }

    @Override
    public void sendToProducer(List<TransactionDto> transactionDataDtos, String topic) {
        transactionDataDtos.forEach(dto -> kafkaProducer.sendTo(topic, dto));
        log.info("Data of Transactions send to topic {}", topic);
    }
}

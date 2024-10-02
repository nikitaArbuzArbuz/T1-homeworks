package ru.t1.java.demo.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.t1.java.demo.kafka.producers.KafkaDefaultProducer;
import ru.t1.java.demo.model.Account;
import ru.t1.java.demo.model.dto.AccountDto;
import ru.t1.java.demo.repository.AccountRepository;
import ru.t1.java.demo.service.AccountService;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final KafkaDefaultProducer kafkaDefaultProducer;
    private final AccountRepository repository;

    @Override
    @Transactional
    public void saveAccount(List<Account> accounts) {
        accounts.forEach(repository::saveAndFlush);
    }

    @Override
    public void sendToProducer(List<AccountDto> accountData, String topic) {
        accountData.forEach(dto -> kafkaDefaultProducer.sendTo(topic, dto));
        log.info("Data of Accounts send to topic {}", topic);
    }
}

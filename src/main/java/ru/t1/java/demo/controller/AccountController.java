package ru.t1.java.demo.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.t1.java.demo.aop.HandlingResult;
import ru.t1.java.demo.aop.LogException;
import ru.t1.java.demo.aop.Metric;
import ru.t1.java.demo.model.dto.AccountDto;
import ru.t1.java.demo.service.AccountService;
import ru.t1.java.demo.util.JsonParser;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AccountController {
    private static final String ACCOUNT_DATA = "ACCOUNT_DATA";
    private final AccountService accountService;
    @Value("${t1.kafka.topic.t1_demo_accounts}")
    private String topic;

    @LogException
    @Metric
    @GetMapping(value = "/account/parse")
    @HandlingResult
    public void parseSource() {
        List<AccountDto> accountData = JsonParser.parseJsonData(ACCOUNT_DATA, AccountDto[].class);
        accountService.sendToProducer(accountData, topic);
    }
}

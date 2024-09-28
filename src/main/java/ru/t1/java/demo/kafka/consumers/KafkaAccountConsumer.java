package ru.t1.java.demo.kafka.consumers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import ru.t1.java.demo.model.Account;
import ru.t1.java.demo.model.dto.AccountDto;
import ru.t1.java.demo.service.AccountService;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class KafkaAccountConsumer {
    private final ru.t1.java.demo.mapper.AccountMapper accountMapper;

    private final AccountService accountService;

    @KafkaListener(id = "${t1.kafka.consumer.group-id-account}",
            topics = "${t1.kafka.topic.t1_demo_accounts}",
            containerFactory = "kafkaAccountListenerContainerFactory")
    public void listenerAccounts(@Payload List<AccountDto> messageList,
                                 Acknowledgment ack,
                                 @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                                 @Header(KafkaHeaders.RECEIVED_KEY) String key) {

        log.debug("Client consumer: Обработка новых сообщений");

        try {
            List<Account> accounts = messageList.stream()
                    .map(accountMapper::toEntity)
                    .toList();
            accountService.saveAccount(accounts);
        } finally {
            ack.acknowledge();
        }


        log.debug("Client consumer: записи обработаны");
    }
}

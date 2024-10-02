package ru.t1.java.demo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.t1.java.demo.kafka.producers.KafkaDefaultProducer;
import ru.t1.java.demo.model.Account;
import ru.t1.java.demo.model.dto.AccountDto;
import ru.t1.java.demo.repository.AccountRepository;
import ru.t1.java.demo.service.impl.AccountServiceImpl;

import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock
    private KafkaDefaultProducer kafkaDefaultProducer;

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountServiceImpl accountService;

    @Test
    void saveAccount_shouldSaveEachAccount() {
        List<Account> accounts = List.of(new Account(), new Account());
        accountService.saveAccount(accounts);
        verify(accountRepository, times(accounts.size())).saveAndFlush(any(Account.class));
    }

    @Test
    void sendToProducer_shouldSendEachAccountToKafka() {
        List<AccountDto> accountDtos = List.of(new AccountDto(), new AccountDto());
        String topic = "test-topic";
        accountService.sendToProducer(accountDtos, topic);
        verify(kafkaDefaultProducer, times(accountDtos.size())).sendTo(eq(topic), any(AccountDto.class));
    }
}

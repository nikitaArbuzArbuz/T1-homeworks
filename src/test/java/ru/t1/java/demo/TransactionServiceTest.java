package ru.t1.java.demo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.t1.java.demo.kafka.producers.KafkaDefaultProducer;
import ru.t1.java.demo.model.Transaction;
import ru.t1.java.demo.model.dto.TransactionDto;
import ru.t1.java.demo.repository.TransactionRepository;
import ru.t1.java.demo.service.impl.TransactionServiceImpl;

import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionServiceImplTest {

    @Mock
    private KafkaDefaultProducer kafkaDefaultProducer;

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionServiceImpl transactionService;

    @Test
    void saveTransaction_shouldSaveEachTransaction() {
        List<Transaction> transactions = List.of(new Transaction(), new Transaction());
        transactionService.saveTransaction(transactions);
        verify(transactionRepository, times(transactions.size())).saveAndFlush(any(Transaction.class));
    }

    @Test
    void sendToProducer_shouldSendEachTransactionToKafka() {
        List<TransactionDto> transactionDtos = List.of(new TransactionDto(), new TransactionDto());
        String topic = "test-topic";
        transactionService.sendToProducer(transactionDtos, topic);
        verify(kafkaDefaultProducer, times(transactionDtos.size())).sendTo(eq(topic), any(TransactionDto.class));
    }
}



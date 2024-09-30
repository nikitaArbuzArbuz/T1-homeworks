package ru.t1.java.demo.mapper;


import org.mapstruct.*;
import ru.t1.java.demo.model.Transaction;
import ru.t1.java.demo.model.dto.TransactionDto;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING)
public interface TransactionMapper {
    Transaction map(TransactionDto transactionDto);

    TransactionDto map(Transaction transaction);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Transaction partialUpdate(TransactionDto transactionDto, @MappingTarget Transaction transaction);
}

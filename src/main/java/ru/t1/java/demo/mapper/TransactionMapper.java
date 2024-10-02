package ru.t1.java.demo.mapper;


import lombok.extern.slf4j.Slf4j;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.t1.java.demo.model.Transaction;
import ru.t1.java.demo.model.dto.TransactionDto;

@Mapper(componentModel = "spring")
@Slf4j
public abstract class TransactionMapper {
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "amount", source = "amount")
    @Mapping(target = "clientId", source = "clientId")
    @Mapping(target = "accountId", source = "accountId")
    public abstract Transaction map(TransactionDto dto);
}

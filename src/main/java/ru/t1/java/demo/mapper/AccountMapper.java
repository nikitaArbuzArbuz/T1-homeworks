package ru.t1.java.demo.mapper;

import org.mapstruct.*;
import ru.t1.java.demo.model.Account;
import ru.t1.java.demo.model.dto.AccountDto;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING)
public interface AccountMapper {
    Account map(AccountDto accountDto);

    AccountDto map(Account account);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Account partialUpdate(AccountDto accountDto, @MappingTarget Account account);
}

package ru.t1.java.demo.mapper;

import lombok.extern.slf4j.Slf4j;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.t1.java.demo.model.Account;
import ru.t1.java.demo.model.dto.AccountDto;

@Mapper(componentModel = "spring")
@Slf4j
public abstract class AccountMapper {
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "accountType", source = "accountType")
    @Mapping(target = "balance", source = "balance")
    public abstract Account map(AccountDto dto);
}

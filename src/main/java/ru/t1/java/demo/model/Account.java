package ru.t1.java.demo.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.AbstractPersistable;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "account")
public class Account extends AbstractPersistable<Long> {

    @Column(name = "account_type")
    @Enumerated(value = EnumType.STRING)
    private AccountType accountType;

    @Column(name = "balance", precision = 19, scale = 2)
    private BigDecimal balance;

    public enum AccountType {
        DEBIT,
        CREDIT
    }
}

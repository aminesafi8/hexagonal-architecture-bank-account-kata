package com.amine.katabankaccount.application.core.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

import static com.amine.katabankaccount.application.core.model.Amount.validate;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {

    private AccountId accountId;

    private BigDecimal balance;

    public void deposit(final Amount amount) {
        validate(amount);
        balance = balance.add(amount.value());
    }

    public boolean withdraw(final Amount amount) {
        validate(amount);

        if (canWithdraw(amount)) {
            balance = balance.subtract(amount.value());
            return true;
        }

        return false;
    }

    private boolean canWithdraw(final Amount amount) {
        return new Amount(balance).isGreaterThanOrEqualTo(amount);
    }

}

package com.amine.katabankaccount.adapter.out.persistence.mapper;

import com.amine.katabankaccount.adapter.out.persistence.entity.AccountEntity;
import com.amine.katabankaccount.application.core.model.Account;
import com.amine.katabankaccount.application.core.model.AccountId;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AccountMapper {

    public static AccountEntity toAccountEntity(final Account account) {
        return AccountEntity.builder()
                .id(account.getAccountId().getId())
                .balance(account.getBalance())
                .build();
    }

    public static Account toAccount(final AccountEntity accountEntity) {
        return Account.builder()
                .accountId(new AccountId(accountEntity.getId()))
                .balance(accountEntity.getBalance())
                .build();
    }
}

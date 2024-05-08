package com.amine.katabankaccount.adapter.out.persistence.mapper;

import com.amine.katabankaccount.adapter.out.persistence.entity.AccountEntity;
import com.amine.katabankaccount.adapter.out.persistence.entity.TransactionEntity;
import com.amine.katabankaccount.application.core.model.AccountId;
import com.amine.katabankaccount.application.core.model.Transaction;
import com.amine.katabankaccount.application.core.model.TransactionId;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TransactionMapper {

    public static TransactionEntity from(final AccountId accountId, final Transaction transaction) {
        return TransactionEntity.builder()
                .account(AccountEntity.builder()
                        .id(accountId.getId())
                        .build())
                .status(transaction.getStatus())
                .dateTime(transaction.getDateTime())
                .type(transaction.getType())
                .amount(transaction.getAmount())
                .build();
    }

    public static Transaction from(final TransactionEntity transactionEntity) {
        return Transaction.builder()
                .transactionId(new TransactionId(transactionEntity.getId()))
                .status(transactionEntity.getStatus())
                .type(transactionEntity.getType())
                .dateTime(transactionEntity.getDateTime())
                .amount(transactionEntity.getAmount())
                .build();
    }
}

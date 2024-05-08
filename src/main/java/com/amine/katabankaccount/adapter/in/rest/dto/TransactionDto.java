package com.amine.katabankaccount.adapter.in.rest.dto;

import com.amine.katabankaccount.application.core.enumeration.TransactionStatus;
import com.amine.katabankaccount.application.core.enumeration.TransactionType;
import com.amine.katabankaccount.application.core.model.Transaction;

import java.time.LocalDateTime;

public record TransactionDto(String transactionId,
                             AmountDto amount,
                             LocalDateTime dateTime,
                             TransactionType type,
                             TransactionStatus status) {


    public static TransactionDto from(final Transaction transaction) {
        return new TransactionDto(transaction.getTransactionId().getId().toString(),
                AmountDto.from(transaction.getAmount()),
                transaction.getDateTime(),
                transaction.getType(), transaction.getStatus());
    }
}

package com.amine.katabankaccount.application.core.model;

import com.amine.katabankaccount.application.core.enumeration.TransactionStatus;
import com.amine.katabankaccount.application.core.enumeration.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {

    private TransactionId transactionId;

    private BigDecimal amount;

    private LocalDateTime dateTime;

    private TransactionType type;

    private TransactionStatus status;

    public static Transaction successful(final BigDecimal amount, final TransactionType transactionType) {
        return from(amount, transactionType)
                .toBuilder()
                .status(TransactionStatus.SUCCESS)
                .build();
    }

    public static Transaction failure(final BigDecimal amount, final TransactionType transactionType) {
        return from(amount, transactionType)
                .toBuilder()
                .status(TransactionStatus.FAILURE)
                .build();
    }

    private static Transaction from(final BigDecimal amount, final TransactionType transactionType) {
        return Transaction.builder()
                .amount(amount)
                .dateTime(LocalDateTime.now())
                .type(transactionType)
                .build();
    }

}

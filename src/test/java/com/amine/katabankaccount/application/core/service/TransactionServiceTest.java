package com.amine.katabankaccount.application.core.service;

import com.amine.katabankaccount.application.core.enumeration.TransactionStatus;
import com.amine.katabankaccount.application.core.enumeration.TransactionType;
import com.amine.katabankaccount.application.core.model.AccountId;
import com.amine.katabankaccount.application.core.model.Transaction;
import com.amine.katabankaccount.application.core.model.TransactionId;
import com.amine.katabankaccount.application.core.port.in.structure.Chunk;
import com.amine.katabankaccount.application.core.port.in.structure.PagePayload;
import com.amine.katabankaccount.application.core.port.out.AllTransactionPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static java.util.Collections.emptyList;
import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @Mock
    AllTransactionPort allTransactionPort;

    @InjectMocks
    TransactionService transactionService;

    @Test
    void should_return_transactions_for_a_given_account() {
        // given
        given(allTransactionPort.loadAllTransactionsByAccountId(any(), any()))
                .willReturn(chunkOfTransactions());

        // when
        Chunk<Transaction> transactions = transactionService.getTransactions(anAccountId(), asPagePayload());

        // then
        verify(allTransactionPort).loadAllTransactionsByAccountId(anAccountId(), asPagePayload());
        assertThat(transactions).isNotNull();
        assertThat(transactions.content())
                .isNotEmpty()
                .hasSize(2);
    }

    @Test
    void should_return_NO_transactions_for_a_given_account() {
        // given
        given(allTransactionPort.loadAllTransactionsByAccountId(any(), any()))
                .willReturn(new Chunk<>(emptyList(), 0));

        // when
        Chunk<Transaction> transactions = transactionService.getTransactions(anAccountId(), asPagePayload());

        // then
        verify(allTransactionPort).loadAllTransactionsByAccountId(anAccountId(), asPagePayload());
        assertThat(transactions).isNotNull();
        assertThat(transactions.content()).isEmpty();
    }

    private PagePayload asPagePayload() {
        return new PagePayload(0, 10);
    }

    private Chunk<Transaction> chunkOfTransactions() {
        final List<Transaction> transactions = List.of(
                Transaction.builder()
                        .transactionId(new TransactionId(randomUUID()))
                        .amount(BigDecimal.valueOf(30))
                        .dateTime(LocalDateTime.of(2023, 10, 5, 10, 20))
                        .status(TransactionStatus.FAILURE)
                        .type(TransactionType.CREDIT)
                        .build(),
                Transaction.builder()
                        .transactionId(new TransactionId(randomUUID()))
                        .amount(BigDecimal.valueOf(40))
                        .dateTime(LocalDateTime.of(2023, 10, 5, 10, 20))
                        .status(TransactionStatus.SUCCESS)
                        .type(TransactionType.DEBIT)
                        .build()
        );
        return new Chunk<>(transactions, 2);
    }

    private AccountId anAccountId() {
        return new AccountId(UUID.fromString("58852d41-0c96-4de3-bada-23dd9db66b69"));
    }
}

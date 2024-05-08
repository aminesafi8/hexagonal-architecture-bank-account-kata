package com.amine.katabankaccount.application.core.service;

import com.amine.katabankaccount.application.core.enumeration.TransactionStatus;
import com.amine.katabankaccount.application.core.enumeration.TransactionType;
import com.amine.katabankaccount.application.core.exception.InsufficientFundsException;
import com.amine.katabankaccount.application.core.model.*;
import com.amine.katabankaccount.application.core.port.in.payload.command.DepositCommand;
import com.amine.katabankaccount.application.core.port.in.payload.command.WithdrawCommand;
import com.amine.katabankaccount.application.core.port.out.LoadAccountPort;
import com.amine.katabankaccount.application.core.port.out.SaveAccountPort;
import com.amine.katabankaccount.application.core.port.out.SaveTransactionPort;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock
    LoadAccountPort loadAccountPort;
    @Mock
    SaveAccountPort saveAccountPort;
    @Mock
    SaveTransactionPort saveTransactionPort;
    @InjectMocks
    AccountService accountService;

    @Test
    @DisplayName("Deposit should succeed")
    void deposit_should_succeed() {
        // given
        final DepositCommand depositCommand = DepositCommand.builder()
                .accountId(new AccountId(anAccountId()))
                .amount(Amount.of(40))
                .build();

        given(loadAccountPort.loadAccount(any()))
                .willReturn(Account.builder()
                        .accountId(new AccountId(anAccountId()))
                        .balance(new BigDecimal(60))
                        .build());

        given(saveTransactionPort.save(any(), any()))
                .willReturn(Transaction.builder().transactionId(new TransactionId(aTransactionId())).build());

        // when
        final Deposit deposit = accountService.deposit(depositCommand);

        // then
        assertThat(deposit).isNotNull();
        assertThat(deposit.getTransactionId()).isEqualTo(aTransactionId().toString());

        ArgumentCaptor<Account> accountArgumentCaptor = ArgumentCaptor.forClass(Account.class);
        verify(saveAccountPort).saveAccount(accountArgumentCaptor.capture());
        assertThat(accountArgumentCaptor.getValue()).isNotNull();
        assertThat(accountArgumentCaptor.getValue().getAccountId().getId()).isEqualTo(anAccountId());
        assertThat(accountArgumentCaptor.getValue().getBalance()).isEqualTo(new BigDecimal(100));
    }

    @Test
    @DisplayName("Withdraw should fail, insufficient funds")
    void withdraw_should_fail_insufficient_funds() {
        // given
        final WithdrawCommand withdrawCommand = WithdrawCommand.builder()
                .accountId(new AccountId(anAccountId()))
                .amount(Amount.of(40))
                .build();

        given(loadAccountPort.loadAccount(any()))
                .willReturn(Account.builder()
                        .accountId(new AccountId(anAccountId()))
                        .balance(new BigDecimal(0))
                        .build());

        given(saveTransactionPort.save(any(), any()))
                .willReturn(Transaction.builder()
                        .transactionId(new TransactionId(aTransactionId()))
                        .build());


        assertThrows(InsufficientFundsException.class, () -> accountService.withdraw(withdrawCommand));

        verifyNoInteractions(saveAccountPort);

        ArgumentCaptor<Transaction> transactionArgumentCaptor = ArgumentCaptor.forClass(Transaction.class);
        verify(saveTransactionPort).save(any(), transactionArgumentCaptor.capture());

        final Transaction transactionArgument = transactionArgumentCaptor.getValue();
        assertThat(transactionArgument).isNotNull();
        assertThat(transactionArgument.getAmount()).isEqualTo(BigDecimal.valueOf(40));
        assertThat(transactionArgument.getStatus()).isEqualTo(TransactionStatus.FAILURE);
        assertThat(transactionArgument.getType()).isEqualTo(TransactionType.CREDIT);
    }


    @Test
    @DisplayName("Withdraw should succeed")
    void withdraw_should_succeed() {
        // given
        final WithdrawCommand withdrawCommand = WithdrawCommand.builder()
                .accountId(new AccountId(anAccountId()))
                .amount(Amount.of(30))
                .build();

        given(loadAccountPort.loadAccount(any()))
                .willReturn(Account.builder()
                        .accountId(new AccountId(anAccountId()))
                        .balance(new BigDecimal(30))
                        .build());

        given(saveTransactionPort.save(any(), any()))
                .willReturn(Transaction.builder()
                        .transactionId(new TransactionId(aTransactionId()))
                        .build());

        // when
        final Withdrawal withdraw = accountService.withdraw(withdrawCommand);

        // then
        assertThat(withdraw).isNotNull();
        assertThat(withdraw.getTransactionId()).isNotNull();

        ArgumentCaptor<Account> accountArgumentCaptor = ArgumentCaptor.forClass(Account.class);
        verify(saveAccountPort).saveAccount(accountArgumentCaptor.capture());
        assertThat(accountArgumentCaptor.getValue()).isNotNull();
        assertThat(accountArgumentCaptor.getValue().getAccountId().getId()).isEqualTo(anAccountId());
        assertThat(accountArgumentCaptor.getValue().getBalance()).isEqualTo(BigDecimal.ZERO);

        ArgumentCaptor<Transaction> transactionArgumentCaptor = ArgumentCaptor.forClass(Transaction.class);
        verify(saveTransactionPort).save(any(), transactionArgumentCaptor.capture());

        final Transaction transactionArgument = transactionArgumentCaptor.getValue();
        assertThat(transactionArgument).isNotNull();
        assertThat(transactionArgument.getAmount()).isEqualTo(BigDecimal.valueOf(30));
        assertThat(transactionArgument.getStatus()).isEqualTo(TransactionStatus.SUCCESS);
        assertThat(transactionArgument.getType()).isEqualTo(TransactionType.CREDIT);
    }

    private UUID anAccountId() {
        return UUID.fromString("58852d41-0c96-4de3-bada-23dd9db66b69");
    }

    private UUID aTransactionId() {
        return UUID.fromString("fb3c67dd-3e25-4281-8f75-b3326012572d");
    }
}

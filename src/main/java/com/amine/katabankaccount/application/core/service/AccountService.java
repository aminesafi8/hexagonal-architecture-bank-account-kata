package com.amine.katabankaccount.application.core.service;


import com.amine.katabankaccount.application.core.annotation.UseCase;
import com.amine.katabankaccount.application.core.enumeration.TransactionType;
import com.amine.katabankaccount.application.core.exception.InsufficientFundsException;
import com.amine.katabankaccount.application.core.model.Account;
import com.amine.katabankaccount.application.core.model.Deposit;
import com.amine.katabankaccount.application.core.model.Transaction;
import com.amine.katabankaccount.application.core.model.Withdrawal;
import com.amine.katabankaccount.application.core.port.in.payload.command.DepositCommand;
import com.amine.katabankaccount.application.core.port.in.payload.command.WithdrawCommand;
import com.amine.katabankaccount.application.core.port.in.usecase.DepositUseCase;
import com.amine.katabankaccount.application.core.port.in.usecase.WithdrawUseCase;
import com.amine.katabankaccount.application.core.port.out.LoadAccountPort;
import com.amine.katabankaccount.application.core.port.out.SaveAccountPort;
import com.amine.katabankaccount.application.core.port.out.SaveTransactionPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.math.BigDecimal;

@UseCase
@Log4j2
@RequiredArgsConstructor
public class AccountService implements DepositUseCase, WithdrawUseCase {

    private final LoadAccountPort loadAccountPort;
    private final SaveAccountPort saveAccountPort;
    private final SaveTransactionPort saveTransactionPort;

    @Override
    public Deposit deposit(final DepositCommand depositCommand) {
        doDeposit(depositCommand);

        final Transaction transaction = handleDepositTransaction(depositCommand);

        return Deposit.builder()
                .transactionId(transaction.getTransactionId().getId().toString())
                .build();
    }

    @Override
    public Withdrawal withdraw(final WithdrawCommand withdrawCommand) {
        final Account account = loadAccountPort.loadAccount(withdrawCommand.getAccountId());

        final boolean hasWithdrawn = doWithdraw(withdrawCommand, account);

        final Transaction transaction = handleWithdrawalTransaction(withdrawCommand, hasWithdrawn, account);

        return Withdrawal.builder()
                .transactionId(transaction.getTransactionId().getId().toString())
                .build();
    }

    private Transaction handleWithdrawalTransaction(final WithdrawCommand withdrawCommand, final boolean hasWithdrawn, final Account account) {
        if (hasWithdrawn) {
            log.info("Operation of withdrawing [{}] from the account [{}] was performed successfully", withdrawCommand.getAmount().value(), withdrawCommand.getAccountId().getId());
            saveAccountPort.saveAccount(account);
            final Transaction transaction = Transaction.successful(withdrawCommand.getAmount().value(), TransactionType.CREDIT);
            return saveTransactionPort.save(account.getAccountId(), transaction);
        } else {
            final BigDecimal withdrawAmountValue = withdrawCommand.getAmount().value();
            log.error("Operation of withdrawing [{}] from the account [{}] was failed", withdrawAmountValue, withdrawCommand.getAccountId().getId());
            final Transaction transaction = Transaction.failure(withdrawAmountValue, TransactionType.CREDIT);
            saveTransactionPort.save(account.getAccountId(), transaction);
            throw new InsufficientFundsException(withdrawAmountValue, account.getBalance());
        }
    }

    private boolean doWithdraw(final WithdrawCommand withdrawCommand, final Account account) {
        return account.withdraw(withdrawCommand.getAmount());
    }

    private Transaction handleDepositTransaction(final DepositCommand depositCommand) {
        final Transaction transaction = Transaction.successful(depositCommand.getAmount().value(), TransactionType.DEBIT);
        return saveTransactionPort.save(depositCommand.getAccountId(), transaction);
    }

    private void doDeposit(final DepositCommand depositCommand) {
        final Account account = loadAccountPort.loadAccount(depositCommand.getAccountId());
        account.deposit(depositCommand.getAmount());
        saveAccountPort.saveAccount(account);
    }


}

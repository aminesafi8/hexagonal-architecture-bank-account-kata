package com.amine.katabankaccount.application.core.service;

import com.amine.katabankaccount.application.core.annotation.UseCase;
import com.amine.katabankaccount.application.core.model.AccountId;
import com.amine.katabankaccount.application.core.model.Transaction;
import com.amine.katabankaccount.application.core.port.in.structure.Chunk;
import com.amine.katabankaccount.application.core.port.in.structure.PagePayload;
import com.amine.katabankaccount.application.core.port.in.usecase.GetTransactionsUseCase;
import com.amine.katabankaccount.application.core.port.out.AllTransactionPort;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class TransactionService implements GetTransactionsUseCase {

    private final AllTransactionPort allTransactionPort;

    @Override
    public Chunk<Transaction> getTransactions(final AccountId accountId, final PagePayload pagePayload) {
        return allTransactionPort.loadAllTransactionsByAccountId(accountId, pagePayload);
    }
}

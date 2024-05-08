package com.amine.katabankaccount.application.core.port.in.usecase;

import com.amine.katabankaccount.application.core.model.AccountId;
import com.amine.katabankaccount.application.core.model.Transaction;
import com.amine.katabankaccount.application.core.port.in.structure.Chunk;
import com.amine.katabankaccount.application.core.port.in.structure.PagePayload;

/**
 * Fetch all the transactions
 */
public interface GetTransactionsUseCase {

    /**
     * Get all the transactions carried out on a given account
     *
     * @param accountId   the account id
     * @param pagePayload size & index of the requested page
     * @return Chunk (portion) of transactions
     */
    Chunk<Transaction> getTransactions(final AccountId accountId, final PagePayload pagePayload);
}

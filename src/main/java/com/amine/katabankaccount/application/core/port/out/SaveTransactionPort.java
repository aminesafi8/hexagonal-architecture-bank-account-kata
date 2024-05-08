package com.amine.katabankaccount.application.core.port.out;

import com.amine.katabankaccount.application.core.model.AccountId;
import com.amine.katabankaccount.application.core.model.Transaction;

/**
 * This port will interact with external component to save transactions
 */
public interface SaveTransactionPort {
    /**
     * @param accountId   the account id for that particular transaction
     * @param transaction the transaction to be saved
     * @return the newly saved transaction
     */
    Transaction save(final AccountId accountId, final Transaction transaction);
}

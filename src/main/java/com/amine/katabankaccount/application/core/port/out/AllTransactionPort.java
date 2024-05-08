package com.amine.katabankaccount.application.core.port.out;

import com.amine.katabankaccount.application.core.model.AccountId;
import com.amine.katabankaccount.application.core.model.Transaction;
import com.amine.katabankaccount.application.core.port.in.structure.Chunk;
import com.amine.katabankaccount.application.core.port.in.structure.PagePayload;

/**
 * This port will interact with external component to fetch all transactions of a given account Id
 */
public interface AllTransactionPort {

    /**
     * @param accountId   the account id to get all its transactions
     * @param pagePayload pagination parameters
     * @return the list of the transactions of the desired account id
     */
    Chunk<Transaction> loadAllTransactionsByAccountId(final AccountId accountId, final PagePayload pagePayload);

}

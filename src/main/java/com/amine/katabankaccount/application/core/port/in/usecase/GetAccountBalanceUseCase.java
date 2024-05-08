package com.amine.katabankaccount.application.core.port.in.usecase;

import com.amine.katabankaccount.application.core.model.Amount;
import com.amine.katabankaccount.application.core.port.in.payload.query.AccountBalanceQuery;

/**
 * Get the balance of a given account
 */
public interface GetAccountBalanceUseCase {

    /**
     * Get an account balance
     *
     * @param accountBalanceQuery request to get the balance
     * @return the account balance
     */
    Amount getAccountBalance(final AccountBalanceQuery accountBalanceQuery);
}

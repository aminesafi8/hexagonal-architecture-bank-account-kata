package com.amine.katabankaccount.application.core.port.out;

import com.amine.katabankaccount.application.core.model.Account;
import com.amine.katabankaccount.application.core.model.AccountId;

/**
 * This port will interact with external component to load an account
 */
public interface LoadAccountPort {
    /**
     * @param accountId the account id that we are looking for
     * @return the account information
     */
    Account loadAccount(final AccountId accountId);
}

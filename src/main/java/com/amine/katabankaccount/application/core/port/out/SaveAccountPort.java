package com.amine.katabankaccount.application.core.port.out;

import com.amine.katabankaccount.application.core.model.Account;

/**
 * This port will interact with external component to save an account
 */
public interface SaveAccountPort {
    /**
     * @param account that will be saved
     * @return the newly saved account
     */
    Account saveAccount(final Account account);
}

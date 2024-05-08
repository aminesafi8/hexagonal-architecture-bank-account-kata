package com.amine.katabankaccount.adapter.in.rest.manager.contract;

import com.amine.katabankaccount.adapter.in.rest.dto.AccountBalanceDto;

import java.util.UUID;

/**
 * An Account Manager to orchestrate all operations to deal with accounts
 */
public interface AccountManager {
    /**
     * @param accountId the account id to search for
     * @return the balance of a given account
     */
    AccountBalanceDto getAccountBalance(final UUID accountId);
}

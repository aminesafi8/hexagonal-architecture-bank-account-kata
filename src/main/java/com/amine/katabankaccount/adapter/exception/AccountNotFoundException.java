package com.amine.katabankaccount.adapter.exception;

import com.amine.katabankaccount.application.core.model.AccountId;

public class AccountNotFoundException extends RuntimeException {

    public AccountNotFoundException(final AccountId accountId) {
        super("Account with Id " + accountId.getId() + " not found");
    }
}

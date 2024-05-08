package com.amine.katabankaccount.application.core.service;

import com.amine.katabankaccount.application.core.annotation.UseCase;
import com.amine.katabankaccount.application.core.model.Account;
import com.amine.katabankaccount.application.core.model.Amount;
import com.amine.katabankaccount.application.core.port.in.payload.query.AccountBalanceQuery;
import com.amine.katabankaccount.application.core.port.in.usecase.GetAccountBalanceUseCase;
import com.amine.katabankaccount.application.core.port.out.LoadAccountPort;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class GetAccountBalanceService implements GetAccountBalanceUseCase {

    private final LoadAccountPort loadAccountPort;

    @Override
    public Amount getAccountBalance(final AccountBalanceQuery accountBalanceQuery) {
        final Account account = loadAccountPort.loadAccount(accountBalanceQuery.getAccountId());
        return new Amount(account.getBalance());
    }
}

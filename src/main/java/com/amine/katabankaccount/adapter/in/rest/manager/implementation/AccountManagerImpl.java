package com.amine.katabankaccount.adapter.in.rest.manager.implementation;

import com.amine.katabankaccount.adapter.in.rest.dto.AccountBalanceDto;
import com.amine.katabankaccount.adapter.in.rest.dto.AmountDto;
import com.amine.katabankaccount.adapter.in.rest.manager.contract.AccountManager;
import com.amine.katabankaccount.application.core.model.AccountId;
import com.amine.katabankaccount.application.core.model.Amount;
import com.amine.katabankaccount.application.core.port.in.payload.query.AccountBalanceQuery;
import com.amine.katabankaccount.application.core.port.in.usecase.GetAccountBalanceUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Log4j2
@RequiredArgsConstructor
public class AccountManagerImpl implements AccountManager {

    private final GetAccountBalanceUseCase getAccountBalanceUseCase;

    @Override
    public AccountBalanceDto getAccountBalance(final UUID accountId) {
        log.info("Request to get the balance for the account id [{}] ", accountId);

        final AccountBalanceQuery accountBalanceQuery = AccountBalanceQuery.builder()
                .accountId(new AccountId(accountId))
                .build();

        final Amount accountBalanceAmount = getAccountBalanceUseCase.getAccountBalance(accountBalanceQuery);

        return new AccountBalanceDto(accountId.toString(), AmountDto.from(accountBalanceAmount));
    }
}

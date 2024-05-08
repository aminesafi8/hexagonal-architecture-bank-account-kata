package com.amine.katabankaccount.adapter.in.rest.manager.implementation;

import com.amine.katabankaccount.adapter.in.rest.dto.DepositDto;
import com.amine.katabankaccount.adapter.in.rest.dto.WithdrawalDto;
import com.amine.katabankaccount.adapter.in.rest.manager.contract.OperationManager;
import com.amine.katabankaccount.adapter.in.rest.payload.DepositRequest;
import com.amine.katabankaccount.adapter.in.rest.payload.WithdrawalRequest;
import com.amine.katabankaccount.application.core.model.AccountId;
import com.amine.katabankaccount.application.core.model.Amount;
import com.amine.katabankaccount.application.core.model.Deposit;
import com.amine.katabankaccount.application.core.model.Withdrawal;
import com.amine.katabankaccount.application.core.port.in.payload.command.DepositCommand;
import com.amine.katabankaccount.application.core.port.in.payload.command.WithdrawCommand;
import com.amine.katabankaccount.application.core.port.in.usecase.DepositUseCase;
import com.amine.katabankaccount.application.core.port.in.usecase.WithdrawUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Log4j2
public class OperationManagerImpl implements OperationManager {

    private final DepositUseCase depositUseCase;
    private final WithdrawUseCase withdrawUseCase;

    @Override
    public DepositDto deposit(final UUID accountId, final DepositRequest depositRequest) {
        log.info("Request to deposit [{}] into the account id [{}] ", depositRequest.getAmount(), accountId);

        final DepositCommand depositCommand = DepositCommand.builder()
                .accountId(new AccountId(accountId))
                .amount(new Amount(depositRequest.getAmount().getValue()))
                .build();

        final Deposit deposit = depositUseCase.deposit(depositCommand);

        return new DepositDto(deposit.getTransactionId());
    }

    @Override
    public WithdrawalDto withdraw(final UUID accountId, final WithdrawalRequest withdrawalRequest) {
        log.info("Request to withdraw [{}] from the account id [{}] ", withdrawalRequest.getAmount().getValue(), accountId);

        final WithdrawCommand withdrawCommand = WithdrawCommand.builder()
                .accountId(new AccountId(accountId))
                .amount(new Amount(withdrawalRequest.getAmount().getValue()))
                .build();

        final Withdrawal withdrawal = withdrawUseCase.withdraw(withdrawCommand);

        return new WithdrawalDto(withdrawal.getTransactionId());
    }
}

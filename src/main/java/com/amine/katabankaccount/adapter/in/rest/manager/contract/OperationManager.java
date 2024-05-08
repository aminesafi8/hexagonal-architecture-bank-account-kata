package com.amine.katabankaccount.adapter.in.rest.manager.contract;

import com.amine.katabankaccount.adapter.in.rest.dto.DepositDto;
import com.amine.katabankaccount.adapter.in.rest.dto.WithdrawalDto;
import com.amine.katabankaccount.adapter.in.rest.payload.DepositRequest;
import com.amine.katabankaccount.adapter.in.rest.payload.WithdrawalRequest;

import java.util.UUID;

/**
 * An Operation Manager to orchestrate deposit & withdrawal logic
 */
public interface OperationManager {
    /**
     * @param accountId      the account id in which we will deposit some money
     * @param depositRequest the deposit request data (amount, ..)
     * @return some information about the deposit operation
     */
    DepositDto deposit(final UUID accountId, final DepositRequest depositRequest);

    /**
     * @param accountId         the account id from which will want to withdraw some money
     * @param withdrawalRequest the withdrawal request data (amount, ..)
     * @return some information about the withdrawal operation
     */
    WithdrawalDto withdraw(final UUID accountId, final WithdrawalRequest withdrawalRequest);
}

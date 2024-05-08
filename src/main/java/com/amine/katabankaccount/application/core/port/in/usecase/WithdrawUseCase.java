package com.amine.katabankaccount.application.core.port.in.usecase;

import com.amine.katabankaccount.application.core.model.Withdrawal;
import com.amine.katabankaccount.application.core.port.in.payload.command.WithdrawCommand;

/**
 * Manages the withdrawal operations from a bank account.
 */
public interface WithdrawUseCase {

    /**
     * Withdraw money from an account
     *
     * @param withdrawCommand money withdrawal request
     * @return some information about the withdrawal operation
     */
    Withdrawal withdraw(final WithdrawCommand withdrawCommand);
}

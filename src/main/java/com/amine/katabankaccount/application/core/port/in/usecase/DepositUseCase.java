package com.amine.katabankaccount.application.core.port.in.usecase;

import com.amine.katabankaccount.application.core.model.Deposit;
import com.amine.katabankaccount.application.core.port.in.payload.command.DepositCommand;

/**
 * Manages the deposit operations into a bank account.
 */
public interface DepositUseCase {

    /**
     * Deposit money in an account
     *
     * @param depositCommand money deposit request
     * @return some information about the deposit operation
     */
    Deposit deposit(final DepositCommand depositCommand);
}

package com.amine.katabankaccount.application.core.exception;

import java.math.BigDecimal;

public class InsufficientFundsException extends RuntimeException {

    public InsufficientFundsException(final BigDecimal withdrawalAmount, final BigDecimal actualBalanceAmount) {
        super("Your current balance is " + actualBalanceAmount + ", but your withdrawal request is " + withdrawalAmount);
    }
}

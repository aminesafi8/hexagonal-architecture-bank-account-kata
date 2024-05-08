package com.amine.katabankaccount.application.core.model;

import com.amine.katabankaccount.application.core.exception.InvalidAmountException;

import java.math.BigDecimal;

import static java.util.Objects.isNull;

public record Amount(BigDecimal value) {

    public static void validate(final Amount amount) {
        if (isNull(amount) || isNull(amount.value())) {
            throw new InvalidAmountException("The amount must not be null");
        }
        if (amount.isNegative()) {
            throw new InvalidAmountException("The amount must not be negative");
        }
        if (amount.isZero()) {
            throw new InvalidAmountException("The amount must not be equal to 0");
        }
    }

    public static Amount of(final long value) {
        return new Amount(new BigDecimal(value));
    }

    public static Amount of(final BigDecimal value) {
        return new Amount(value);
    }


    public boolean isGreaterThanOrEqualTo(final Amount amount) {
        return this.value.compareTo(amount.value()) >= 0;
    }

    public boolean isZero() {
        return this.value.compareTo(BigDecimal.ZERO) == 0;
    }

    public boolean isNegative() {
        return this.value.compareTo(BigDecimal.ZERO) < 0;
    }

}

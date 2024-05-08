package com.amine.katabankaccount.adapter.in.rest.dto;

import com.amine.katabankaccount.application.core.model.Amount;

import java.math.BigDecimal;

public record AmountDto(BigDecimal value) {

    public static AmountDto from(final Amount amount) {
        return new AmountDto(amount.value());
    }

    public static AmountDto from(final BigDecimal amount) {
        return new AmountDto(amount);
    }
}

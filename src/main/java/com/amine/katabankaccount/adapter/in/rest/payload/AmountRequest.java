package com.amine.katabankaccount.adapter.in.rest.payload;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class AmountRequest {

    @NotNull
    @DecimalMin(value = "0", inclusive = false, message = "Invalid amount")
    private BigDecimal value;
}

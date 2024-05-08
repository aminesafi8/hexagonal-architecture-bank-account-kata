package com.amine.katabankaccount.application.core.port.in.payload.query;

import com.amine.katabankaccount.application.core.model.AccountId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountBalanceQuery {

    private AccountId accountId;
}

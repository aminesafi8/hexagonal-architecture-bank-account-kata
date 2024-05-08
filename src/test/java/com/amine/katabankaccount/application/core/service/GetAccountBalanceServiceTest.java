package com.amine.katabankaccount.application.core.service;

import com.amine.katabankaccount.application.core.model.Account;
import com.amine.katabankaccount.application.core.model.AccountId;
import com.amine.katabankaccount.application.core.model.Amount;
import com.amine.katabankaccount.application.core.port.in.payload.query.AccountBalanceQuery;
import com.amine.katabankaccount.application.core.port.out.LoadAccountPort;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class GetAccountBalanceServiceTest {

    @Mock
    LoadAccountPort loadAccountPort;

    @InjectMocks
    GetAccountBalanceService getAccountBalanceService;

    @ParameterizedTest
    @MethodSource("dataProvider")
    void should_return_account_balance(final Account mockedAccount, final Amount expectedAmount) {

        // Given
        given(loadAccountPort.loadAccount(any()))
                .willReturn(mockedAccount);

        final AccountBalanceQuery accountBalanceQuery = new AccountBalanceQuery(mockedAccount.getAccountId());

        // when
        final Amount actualAmount = getAccountBalanceService.getAccountBalance(accountBalanceQuery);

        // then
        verify(loadAccountPort).loadAccount(accountBalanceQuery.getAccountId());

        assertThat(actualAmount)
                .isNotNull()
                .isEqualTo(expectedAmount);

        assertThat(actualAmount.value()).isEqualTo(expectedAmount.value());
    }

    private static Stream<Arguments> dataProvider() {
        return Stream.of(
                Arguments.of(createAccountWithBalance(new BigDecimal(30)), Amount.of(30)),
                Arguments.of(createAccountWithBalance(new BigDecimal(0)), Amount.of(0)),
                Arguments.of(createAccountWithBalance(new BigDecimal(99)), Amount.of(99))
        );
    }

    private static Account createAccountWithBalance(BigDecimal balance) {
        return Account.builder()
                .accountId(new AccountId(anAccountId()))
                .balance(balance)
                .build();
    }

    private static UUID anAccountId() {
        return UUID.fromString("58852d41-0c96-4de3-bada-23dd9db66b69");
    }
}

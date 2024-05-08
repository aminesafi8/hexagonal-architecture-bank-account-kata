package com.amine.katabankaccount.application.core.model;

import com.amine.katabankaccount.application.core.exception.InvalidAmountException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static java.math.BigDecimal.valueOf;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class AccountTest {

    @ParameterizedTest
    @MethodSource("validWithdrawalAmounts")
    void should_withdraw_valid_amount(final BigDecimal balance, final BigDecimal withdrawal, final BigDecimal remaining) {
        Account account = new Account();
        account.setBalance(balance);

        boolean hasWithdrawn = account.withdraw(Amount.of(withdrawal));

        assertTrue(hasWithdrawn);
        assertThat(account.getBalance()).isEqualTo(remaining);
    }

    @ParameterizedTest
    @MethodSource("invalidWithdrawalAmounts")
    void should_not_withdraw_invalid_amount(final BigDecimal balance, final BigDecimal withdrawal, final BigDecimal remainingBalance) {
        Account account = new Account();
        account.setBalance(balance);

        assertThrows(InvalidAmountException.class, () -> {
            account.withdraw(Amount.of(withdrawal));
        });

        assertThat(account.getBalance()).isEqualTo(remainingBalance);
    }

    @ParameterizedTest
    @MethodSource("insufficientFunds")
    void test_withdraw_with_insufficient_funds(final BigDecimal balance, final BigDecimal withdrawal) {
        Account account = new Account();
        account.setBalance(balance);

        boolean result = account.withdraw(Amount.of(withdrawal));

        assertFalse(result);
        assertThat(account.getBalance()).isEqualTo(balance);
    }

    @ParameterizedTest
    @MethodSource("validDepositAmounts")
    void should_deposit_valid_amount(final BigDecimal actualBalance, final BigDecimal deposit, final BigDecimal newBalance) {
        Account account = new Account();
        account.setBalance(actualBalance);

        account.deposit(Amount.of(deposit));

        assertThat(account.getBalance()).isEqualTo(newBalance);
    }

    @ParameterizedTest
    @MethodSource("invalidDepositAmounts")
    void should_not_deposit_invalid_amount(final BigDecimal actualBalance, final BigDecimal deposit, final BigDecimal newBalance) {
        Account account = new Account();
        account.setBalance(actualBalance);

        assertThrows(InvalidAmountException.class, () -> {
            account.deposit(Amount.of(deposit));
        });

        assertThat(account.getBalance()).isEqualTo(newBalance);
    }

    static Stream<Arguments> invalidDepositAmounts() {
        return Stream.of(
                Arguments.of(valueOf(50), null, valueOf(50)),
                Arguments.of(valueOf(10), valueOf(-10), valueOf(10)),
                Arguments.of(valueOf(20), valueOf(0), valueOf(20)),
                Arguments.of(valueOf(0), valueOf(0), valueOf(0))
        );
    }

    static Stream<Arguments> validDepositAmounts() {
        return Stream.of(
                Arguments.of(valueOf(0), valueOf(20), valueOf(20)),
                Arguments.of(valueOf(100), valueOf(1), valueOf(101)),
                Arguments.of(valueOf(0.03), valueOf(4), valueOf(4.03))
        );
    }

    private static Stream<Arguments> insufficientFunds() {
        return Stream.of(
                Arguments.of(valueOf(0), valueOf(0.01)),
                Arguments.of(valueOf(40), valueOf(40.01))
        );
    }

    static Stream<Arguments> validWithdrawalAmounts() {
        return Stream.of(
                Arguments.of(valueOf(500), valueOf(100), valueOf(400)),
                Arguments.of(valueOf(500), valueOf(500), valueOf(0)),
                Arguments.of(valueOf(500), valueOf(499.99), valueOf(0.01))
        );
    }

    static Stream<Arguments> invalidWithdrawalAmounts() {
        return Stream.of(
                Arguments.of(valueOf(500), null, valueOf(500)),
                Arguments.of(valueOf(500), valueOf(-50), valueOf(500)),
                Arguments.of(valueOf(500), valueOf(0), valueOf(500))
        );
    }

}

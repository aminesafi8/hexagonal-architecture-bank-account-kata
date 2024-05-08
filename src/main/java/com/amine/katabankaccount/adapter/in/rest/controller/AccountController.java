package com.amine.katabankaccount.adapter.in.rest.controller;

import com.amine.katabankaccount.adapter.in.rest.dto.AccountBalanceDto;
import com.amine.katabankaccount.adapter.in.rest.dto.DepositDto;
import com.amine.katabankaccount.adapter.in.rest.dto.TransactionDto;
import com.amine.katabankaccount.adapter.in.rest.dto.WithdrawalDto;
import com.amine.katabankaccount.adapter.in.rest.manager.contract.AccountManager;
import com.amine.katabankaccount.adapter.in.rest.manager.contract.OperationManager;
import com.amine.katabankaccount.adapter.in.rest.manager.contract.TransactionManager;
import com.amine.katabankaccount.adapter.in.rest.payload.DepositRequest;
import com.amine.katabankaccount.adapter.in.rest.payload.WithdrawalRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@Tag(name = "Account", description = "Endpoints to interact with an account")
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountManager accountManager;
    private final TransactionManager transactionManager;
    private final OperationManager operationManager;

    @Operation(summary = "Get the account balance")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the account balance",
                    content = {@Content(mediaType = "application/json", schema =
                    @Schema(implementation = AccountBalanceDto.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid account id", content = @Content),
            @ApiResponse(responseCode = "404", description = "Account not found", content = @Content)})
    @GetMapping("/{accountId}/balance")
    public ResponseEntity<AccountBalanceDto> accountBalance(@Parameter(description = "The account id that we want to get its balance")
                                                            @PathVariable final UUID accountId) {
        return ResponseEntity.ok(accountManager.getAccountBalance(accountId));
    }

    @Operation(summary = "Deposit some money in an account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Money deposited successfully",
                    content = {@Content(mediaType = "application/json", schema =
                    @Schema(implementation = DepositDto.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid account id and/or deposit request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Account not found", content = @Content)})
    @PatchMapping("/{accountId}/deposit")
    public ResponseEntity<DepositDto> deposit(@Parameter(description = "The account id in which we want to deposit some money")
                                              @PathVariable final UUID accountId,
                                              @Valid @RequestBody final DepositRequest depositRequest) {
        final DepositDto depositDto = operationManager.deposit(accountId, depositRequest);
        return ResponseEntity.ok(depositDto);
    }

    @Operation(summary = "Withdraw some money from an account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Money withdrawn successfully",
                    content = {@Content(mediaType = "application/json", schema =
                    @Schema(implementation = WithdrawalDto.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid account id and/or withdraw request", content = @Content),
            @ApiResponse(responseCode = "403", description = "Insufficient funds", content = @Content),
            @ApiResponse(responseCode = "404", description = "Account not found", content = @Content)})
    @PatchMapping("/{accountId}/withdrawal")
    public ResponseEntity<WithdrawalDto> withdrawal(@Parameter(description = "The account id from which we want to withdraw some money")
                                                    @PathVariable final UUID accountId,
                                                    @Valid @RequestBody final WithdrawalRequest withdrawalRequest) {
        final WithdrawalDto withdrawal = operationManager.withdraw(accountId, withdrawalRequest);
        return ResponseEntity.ok(withdrawal);
    }

    @Operation(summary = "Get an account transactions")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "A page of transactions",
                    content = {@Content(mediaType = "application/json", schema =
                    @Schema(implementation = Page.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid account id", content = @Content),
            @ApiResponse(responseCode = "404", description = "Account not found", content = @Content)})
    @GetMapping("/{accountId}/transactions")
    public ResponseEntity<Page<TransactionDto>> getTransactionsByAccountId(@Parameter(description = "The account id that we want to fetch its transactions ")
                                                                           @PathVariable final UUID accountId,
                                                                           @ParameterObject final Pageable pageable) {
        final Page<TransactionDto> pageOfTransactions = transactionManager.getTransactionsByAccountId(accountId, pageable);
        return ResponseEntity.ok(pageOfTransactions);
    }

}

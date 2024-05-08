package com.amine.katabankaccount.adapter.in.rest.manager.implementation;

import com.amine.katabankaccount.adapter.in.rest.dto.TransactionDto;
import com.amine.katabankaccount.adapter.in.rest.manager.contract.TransactionManager;
import com.amine.katabankaccount.application.core.model.AccountId;
import com.amine.katabankaccount.application.core.model.Transaction;
import com.amine.katabankaccount.application.core.port.in.structure.Chunk;
import com.amine.katabankaccount.application.core.port.in.structure.PagePayload;
import com.amine.katabankaccount.application.core.port.in.usecase.GetTransactionsUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Log4j2
@RequiredArgsConstructor
public class TransactionManagerImpl implements TransactionManager {

    private final GetTransactionsUseCase getTransactionsUseCase;

    @Override
    public Page<TransactionDto> getTransactionsByAccountId(final UUID accountId, final Pageable pageable) {
        log.info("Request to get [{}] transactions from page [{}] of the account id [{}] ", pageable.getPageSize(), pageable.getPageNumber(), accountId);

        final PagePayload pagePayload = new PagePayload(pageable.getPageNumber(), pageable.getPageSize());

        final Chunk<Transaction> chunkOfTransactions = getTransactionsUseCase.getTransactions(new AccountId(accountId), pagePayload);

        final List<TransactionDto> transactionDtoList = chunkOfTransactions.content().stream()
                .map(TransactionDto::from)
                .toList();

        return PageableExecutionUtils.getPage(transactionDtoList, pageable, chunkOfTransactions::totalElements);
    }
}

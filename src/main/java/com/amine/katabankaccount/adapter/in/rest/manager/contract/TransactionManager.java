package com.amine.katabankaccount.adapter.in.rest.manager.contract;

import com.amine.katabankaccount.adapter.in.rest.dto.TransactionDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

/**
 * A Transaction Manager to orchestrate all operations to deal with transactions
 */
public interface TransactionManager {
    /**
     * @param accountId the account id to get all its transactions
     * @param pageable  contains the pagination information
     * @return a page of transactions of a given account id
     */
    Page<TransactionDto> getTransactionsByAccountId(final UUID accountId, final Pageable pageable);
}

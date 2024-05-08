package com.amine.katabankaccount.adapter.out.persistence.adapter;

import com.amine.katabankaccount.adapter.exception.AccountNotFoundException;
import com.amine.katabankaccount.adapter.out.persistence.entity.AccountEntity;
import com.amine.katabankaccount.adapter.out.persistence.entity.TransactionEntity;
import com.amine.katabankaccount.adapter.out.persistence.mapper.TransactionMapper;
import com.amine.katabankaccount.adapter.out.persistence.repository.AccountJpaRepository;
import com.amine.katabankaccount.adapter.out.persistence.repository.TransactionJpaRepository;
import com.amine.katabankaccount.application.core.model.AccountId;
import com.amine.katabankaccount.application.core.model.Transaction;
import com.amine.katabankaccount.application.core.port.in.structure.Chunk;
import com.amine.katabankaccount.application.core.port.in.structure.PagePayload;
import com.amine.katabankaccount.application.core.port.out.AllTransactionPort;
import com.amine.katabankaccount.application.core.port.out.SaveTransactionPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class TransactionJpaAdapter implements SaveTransactionPort, AllTransactionPort {

    private final TransactionJpaRepository transactionJpaRepository;
    private final AccountJpaRepository accountJpaRepository;

    @Override
    public Transaction save(final AccountId accountId, final Transaction transaction) {
        final TransactionEntity transactionEntity = TransactionMapper.from(accountId, transaction);
        final TransactionEntity savedTransactionEntity = transactionJpaRepository.save(transactionEntity);
        return TransactionMapper.from(savedTransactionEntity);
    }

    @Override
    public Chunk<Transaction> loadAllTransactionsByAccountId(final AccountId accountId, final PagePayload pagePayload) {
        final AccountEntity accountEntity = accountJpaRepository.findById(accountId.getId())
                .orElseThrow(() -> new AccountNotFoundException(accountId));

        final Page<TransactionEntity> pageOfTransactions = transactionJpaRepository.findByAccount_Id(accountEntity.getId(), PageRequest.of(pagePayload.page(), pagePayload.size()));

        final List<Transaction> transactions = pageOfTransactions.getContent().stream().map(TransactionMapper::from).toList();
        return new Chunk<>(transactions, pageOfTransactions.getTotalElements());
    }

}

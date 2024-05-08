package com.amine.katabankaccount.adapter.out.persistence.adapter;

import com.amine.katabankaccount.adapter.exception.AccountNotFoundException;
import com.amine.katabankaccount.adapter.out.persistence.entity.AccountEntity;
import com.amine.katabankaccount.adapter.out.persistence.mapper.AccountMapper;
import com.amine.katabankaccount.adapter.out.persistence.repository.AccountJpaRepository;
import com.amine.katabankaccount.application.core.model.Account;
import com.amine.katabankaccount.application.core.model.AccountId;
import com.amine.katabankaccount.application.core.port.out.LoadAccountPort;
import com.amine.katabankaccount.application.core.port.out.SaveAccountPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AccountJpaAdapter implements SaveAccountPort, LoadAccountPort {

    private final AccountJpaRepository accountJpaRepository;

    @Override
    public Account loadAccount(final AccountId accountId) {
        return accountJpaRepository.findById(accountId.getId())
                .map(AccountMapper::toAccount)
                .orElseThrow(() -> new AccountNotFoundException(accountId));
    }

    @Override
    public Account saveAccount(final Account account) {
        final AccountEntity accountEntity = AccountMapper.toAccountEntity(account);
        final AccountEntity savedAccount = accountJpaRepository.save(accountEntity);
        return AccountMapper.toAccount(savedAccount);
    }
}

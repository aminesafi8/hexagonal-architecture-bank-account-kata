package com.amine.katabankaccount.adapter.out.persistence.repository;

import com.amine.katabankaccount.adapter.out.persistence.entity.TransactionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * interface defines a Spring Data JPA repository for managing "Transaction" entities
 */
@Repository
public interface TransactionJpaRepository extends JpaRepository<TransactionEntity, UUID> {
    Page<TransactionEntity> findByAccount_Id(final UUID accountId, final Pageable pageable);
}

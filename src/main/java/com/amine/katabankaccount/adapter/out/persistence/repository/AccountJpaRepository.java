package com.amine.katabankaccount.adapter.out.persistence.repository;

import com.amine.katabankaccount.adapter.out.persistence.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * interface defines a Spring Data JPA repository for managing "Account" entities
 */
@Repository
public interface AccountJpaRepository extends JpaRepository<AccountEntity, UUID> {
}

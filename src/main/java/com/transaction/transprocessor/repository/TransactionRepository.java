package com.transaction.transprocessor.repository;

import com.transaction.transprocessor.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {

    List<Transaction> findByAccountId(UUID accountId);
}

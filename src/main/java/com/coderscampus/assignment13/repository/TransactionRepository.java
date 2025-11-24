package com.coderscampus.assignment13.repository;

import com.coderscampus.assignment13.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    /**
     * Find all transactions for a specific account
     * Useful for displaying account transaction history
     */
    List<Transaction> findByAccountAccountId(Long accountId);

    /**
     * Find all transactions for a specific account, ordered by date (newest first)
     * This will be used when we implement transaction display
     */
    @Query("select t from Transaction t"
            + " where t.account.accountId = :accountId"
            + " order by t.transactionDate desc")
    List<Transaction> findByAccountIdOrderByDateDesc(@Param("accountId") Long accountId);

    // JpaRepository provides:
    // - save(Transaction transaction)
    // - findById(Long transactionId)
    // - deleteById(Long transactionId)
    // - findAll()
}

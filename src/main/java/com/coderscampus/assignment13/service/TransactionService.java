package com.coderscampus.assignment13.service;

import com.coderscampus.assignment13.domain.Account;
import com.coderscampus.assignment13.domain.Transaction;
import com.coderscampus.assignment13.repository.AccountRepository;
import com.coderscampus.assignment13.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TransactionService {

    private static final String DEPOSIT_TYPE = "D";
    private static final String WITHDRAWAL_TYPE = "W";

    @Autowired
    private TransactionRepository transactionRepo;

    @Autowired
    private AccountRepository accountRepo;

    public Transaction findById(Long transactionId) {
        Optional<Transaction> transactionOpt = transactionRepo.findById(transactionId);
        return transactionOpt.orElse(new Transaction());
    }

    public List<Transaction> findTransactionsByAccountId(Long accountId) {
        return transactionRepo.findByAccountIdOrderByDateDesc(accountId);
    }

    public Transaction createTransaction(Long accountId, Double amount, String type) {
        Optional<Account> accountOpt = accountRepo.findById(accountId);

        if (accountOpt.isPresent()) {
            Account account = accountOpt.get();

            Transaction transaction = new Transaction();
            transaction.setAmount(amount);
            transaction.setType(type);
            transaction.setTransactionDate(LocalDateTime.now());
            transaction.setAccount(account);

            // Add transaction to account's list (bidirectional)
            account.getTransactions().add(transaction);

            return transactionRepo.save(transaction);
        }

        return null;
    }

    public Double calculateAccountBalance(Long accountId) {
        List<Transaction> transactions = transactionRepo.findByAccountAccountId(accountId);

        double balance = 0.0;
        for (Transaction t : transactions) {
            if (DEPOSIT_TYPE.equals(t.getType())) {
                balance += t.getAmount();
            } else if (WITHDRAWAL_TYPE.equals(t.getType())) {
                balance -= t.getAmount();
            }
        }

        return balance;
    }

    public Transaction saveTransaction(Transaction transaction) {
        return transactionRepo.save(transaction);
    }

    public void deleteTransaction(Long transactionId) {
        transactionRepo.deleteById(transactionId);
    }
}

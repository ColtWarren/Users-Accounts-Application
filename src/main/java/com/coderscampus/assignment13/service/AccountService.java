package com.coderscampus.assignment13.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.coderscampus.assignment13.domain.Account;
import com.coderscampus.assignment13.domain.User;
import com.coderscampus.assignment13.repository.AccountRepository;
import com.coderscampus.assignment13.repository.UserRepository;

@Service
@Transactional
public class AccountService {

    @Autowired
    private AccountRepository accountRepo;

    @Autowired
    private UserRepository userRepo;

    public Account findById(Long accountId) {
        Optional<Account> accountOpt = accountRepo.findById(accountId);
        return accountOpt.orElse(new Account());
    }

    public Account saveAccount(Account account) {
        return accountRepo.save(account);
    }

    public Account createAccountForUser(Long userId) {
        Optional<User> userOpt = userRepo.findById(userId);

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            Account newAccount = new Account();

            int accountNumber = user.getAccounts().size() + 1;
            newAccount.setAccountName("Account #" + accountNumber);

            newAccount.getUsers().add(user);
            user.getAccounts().add(newAccount);

            accountRepo.save(newAccount);
            userRepo.save(user);

            return newAccount;
        }

        return null;
    }

    public void deleteAccount(Long accountId) {
        accountRepo.deleteById(accountId);
    }
}
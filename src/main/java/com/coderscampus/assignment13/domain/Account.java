package com.coderscampus.assignment13.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "accounts")
public class Account {
    private Long accountId;
    private String accountName;
    private List<Transaction> transactions = new ArrayList<>();
    private List<User> users = new ArrayList<>();

    public Account() {
        // Default constructor - required by JPA
    }

    public Account(Long accountId, String accountName, List<Transaction> transactions, List<User> users) {
        this.accountId = accountId;
        this.accountName = accountName;
        this.transactions = transactions != null ? transactions : new ArrayList<>();
        this.users = users != null ? users : new ArrayList<>();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    @Column(length = 100)
    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    @OneToMany(mappedBy = "account")
    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    @ManyToMany(mappedBy = "accounts")
    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountId=" + accountId +
                ", accountName='" + accountName + '\'' +
                '}';  // Removed collections from toString to avoid circular references
    }
}

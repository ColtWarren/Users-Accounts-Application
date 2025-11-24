sql-- ============================================
-- Users & Accounts Application - Database Setup
-- ============================================
-- Author: Colt Warren
-- Description: Complete database schema for the Users & Accounts banking application
-- Version: 1.0
-- Last Updated: 2025-11-23
-- ============================================

-- ============================================
-- 1. DATABASE CREATION
-- ============================================

-- Drop database if it exists (CAUTION: This will delete all data!)
-- Uncomment the line below only if you want to start fresh
-- DROP DATABASE IF EXISTS users_accounts_application;

-- Create the database
CREATE DATABASE IF NOT EXISTS users_accounts_application
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

-- Use the database
USE users_accounts_application;

-- ============================================
-- 2. USER & PERMISSIONS SETUP
-- ============================================

-- Create application user (if not exists)
-- Note: Change the password in production!
CREATE USER IF NOT EXISTS 'example_user'@'localhost' IDENTIFIED BY 'password123';

-- Grant privileges
GRANT ALL PRIVILEGES ON users_accounts_application.* TO 'example_user'@'localhost';

-- Apply privilege changes
FLUSH PRIVILEGES;

-- ============================================
-- 3. TABLE CREATION
-- ============================================

-- Drop tables if they exist (to ensure clean setup)
-- Order matters due to foreign key constraints
DROP TABLE IF EXISTS transactions;
DROP TABLE IF EXISTS user_account;
DROP TABLE IF EXISTS addresses;
DROP TABLE IF EXISTS accounts;
DROP TABLE IF EXISTS users;

-- --------------------------------------------
-- Table: users
-- Description: Stores user account information
-- --------------------------------------------
CREATE TABLE users (
    user_id BIGINT NOT NULL AUTO_INCREMENT,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    created_date DATE DEFAULT NULL,
    PRIMARY KEY (user_id),
    UNIQUE KEY UK_username (username)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------
-- Table: addresses
-- Description: User addresses (One-to-One with users)
-- --------------------------------------------
CREATE TABLE addresses (
    user_id BIGINT NOT NULL,
    address_line1 VARCHAR(255) DEFAULT NULL,
    address_line2 VARCHAR(255) DEFAULT NULL,
    city VARCHAR(255) DEFAULT NULL,
    region VARCHAR(255) DEFAULT NULL,
    country VARCHAR(255) DEFAULT NULL,
    zip_code VARCHAR(255) DEFAULT NULL,
    PRIMARY KEY (user_id),
    CONSTRAINT FK_address_user FOREIGN KEY (user_id)
        REFERENCES users (user_id)
        ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------
-- Table: accounts
-- Description: Bank accounts
-- --------------------------------------------
CREATE TABLE accounts (
    account_id BIGINT NOT NULL AUTO_INCREMENT,
    account_name VARCHAR(255) NOT NULL,
    PRIMARY KEY (account_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------
-- Table: user_account
-- Description: Join table for Many-to-Many relationship between users and accounts
-- --------------------------------------------
CREATE TABLE user_account (
    user_id BIGINT NOT NULL,
    account_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, account_id),
    KEY FK_account (account_id),
    CONSTRAINT FK_user_account_user FOREIGN KEY (user_id)
        REFERENCES users (user_id)
        ON DELETE CASCADE,
    CONSTRAINT FK_user_account_account FOREIGN KEY (account_id)
        REFERENCES accounts (account_id)
        ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------
-- Table: transactions
-- Description: Account transactions (One-to-Many with accounts)
-- --------------------------------------------
CREATE TABLE transactions (
    transaction_id BIGINT NOT NULL AUTO_INCREMENT,
    amount DECIMAL(19,2) DEFAULT NULL,
    transaction_date DATETIME(6) DEFAULT NULL,
    account_id BIGINT NOT NULL,
    PRIMARY KEY (transaction_id),
    KEY FK_transaction_account (account_id),
    CONSTRAINT FK_transaction_account FOREIGN KEY (account_id)
        REFERENCES accounts (account_id)
        ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================
-- 4. INDEXES (Optional - for performance)
-- ============================================

-- Add index on username for faster lookups
CREATE INDEX IDX_users_username ON users(username);

-- Add index on account_name for faster searches
CREATE INDEX IDX_accounts_name ON accounts(account_name);

-- Add index on transaction_date for faster filtering
CREATE INDEX IDX_transactions_date ON transactions(transaction_date);

-- ============================================
-- 5. SAMPLE DATA (Optional - for testing)
-- ============================================

-- Uncomment below to insert sample data for testing

/*
-- Insert sample user
INSERT INTO users (username, password, name, created_date)
VALUES ('demo_user', 'password123', 'Demo User', CURDATE());

-- Get the user_id
SET @user_id = LAST_INSERT_ID();

-- Insert sample address
INSERT INTO addresses (user_id, address_line1, city, region, country, zip_code)
VALUES (@user_id, '123 Main Street', 'Demo City', 'Demo State', 'USA', '12345');

-- Insert sample accounts
INSERT INTO accounts (account_name) VALUES ('Checking Account');
SET @checking_id = LAST_INSERT_ID();

INSERT INTO accounts (account_name) VALUES ('Savings Account');
SET @savings_id = LAST_INSERT_ID();

-- Link accounts to user
INSERT INTO user_account (user_id, account_id) VALUES (@user_id, @checking_id);
INSERT INTO user_account (user_id, account_id) VALUES (@user_id, @savings_id);

-- Insert sample transactions
INSERT INTO transactions (amount, transaction_date, account_id)
VALUES (1000.00, NOW(), @checking_id);

INSERT INTO transactions (amount, transaction_date, account_id)
VALUES (-50.00, NOW(), @checking_id);
*/

-- ============================================
-- 6. VERIFICATION QUERIES
-- ============================================

-- Show all tables
SHOW TABLES;

-- Show table structures
-- DESCRIBE users;
-- DESCRIBE addresses;
-- DESCRIBE accounts;
-- DESCRIBE user_account;
-- DESCRIBE transactions;

-- ============================================
-- Setup Complete!
-- ============================================
-- To use this schema:
-- 1. Run this entire script in MySQL
-- 2. Update application.properties with your database credentials
-- 3. Start the Spring Boot application
-- 4. Hibernate will verify the schema matches
-- ============================================
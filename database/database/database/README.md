# Database Documentation

## Overview

This folder contains the database schema and setup scripts for the Users & Accounts Application.

---

## Files

- **`schema.sql`** - Complete database setup script including:
    - Database creation
    - User and permissions setup
    - Table structures
    - Foreign key relationships
    - Indexes
    - Sample data (commented out)

---

## Quick Setup

### Prerequisites
- MySQL 8.0 or higher installed
- MySQL command line or MySQL Workbench

### Setup Steps

**Method 1: Using MySQL Command Line**
```bash
# Navigate to the database folder
cd database/

# Run the schema script
mysql -u root -p < schema.sql
```

**Method 2: Using MySQL Workbench**

1. Open MySQL Workbench
2. Connect to your MySQL server
3. Go to **File → Open SQL Script**
4. Select `schema.sql`
5. Click the lightning bolt ⚡ to execute

---

## Database Structure

### Entity Relationship Diagram
```
┌─────────────┐         ┌──────────────┐
│    User     │────────▶│   Address    │
│             │  1:1    │              │
└─────────────┘         └──────────────┘
      │
      │ M:M
      │
      ▼
┌─────────────┐         ┌──────────────┐
│user_account │         │ Transaction  │
│ (join table)│         │              │
└─────────────┘         └──────────────┘
      │                        ▲
      │                        │ 1:M
      ▼                        │
┌─────────────┐                │
│   Account   │────────────────┘
│             │
└─────────────┘
```

---

## Tables

### users
Primary table storing user information.

**Columns:**
- `user_id` (PK) - Auto-increment primary key
- `username` - Unique username (indexed)
- `password` - User password
- `name` - Full name
- `created_date` - Account creation date

### addresses
Stores user addresses with One-to-One relationship to users.

**Columns:**
- `user_id` (PK, FK) - References users.user_id
- `address_line1` - Primary address
- `address_line2` - Secondary address (optional)
- `city` - City
- `region` - State/Province
- `country` - Country
- `zip_code` - Postal code

**Foreign Keys:**
- `FK_address_user` - CASCADE DELETE

### accounts
Bank account information.

**Columns:**
- `account_id` (PK) - Auto-increment primary key
- `account_name` - Account type/name (indexed)

### user_account
Join table for Many-to-Many relationship between users and accounts.

**Columns:**
- `user_id` (PK, FK) - References users.user_id
- `account_id` (PK, FK) - References accounts.account_id

**Foreign Keys:**
- `FK_user_account_user` - CASCADE DELETE
- `FK_user_account_account` - CASCADE DELETE

### transactions
Transaction records for accounts.

**Columns:**
- `transaction_id` (PK) - Auto-increment primary key
- `amount` - Transaction amount (DECIMAL)
- `transaction_date` - Timestamp (indexed)
- `account_id` (FK) - References accounts.account_id

**Foreign Keys:**
- `FK_transaction_account` - CASCADE DELETE

---

## Configuration

### Database Credentials

Default credentials (defined in schema.sql):
```
Database: users_accounts_application
Username: example_user
Password: password123
```

**⚠️ IMPORTANT:** Change the password in production!

### Character Set
- **Charset:** utf8mb4
- **Collation:** utf8mb4_unicode_ci

This ensures proper support for international characters and emojis.

---

## Sample Data

The schema includes commented-out sample data. To use it:

1. Open `schema.sql`
2. Find section **"5. SAMPLE DATA"**
3. Uncomment the INSERT statements
4. Run the script

This will create:
- 1 demo user
- 1 address
- 2 accounts (Checking & Savings)
- 2 sample transactions

---

## Maintenance

### Backup Database
```bash
mysqldump -u example_user -p users_accounts_application > backup.sql
```

### Restore Database
```bash
mysql -u example_user -p users_accounts_application < backup.sql
```

### Reset Database

To completely reset the database:
```sql
DROP DATABASE users_accounts_application;
-- Then run schema.sql again
```

---

## Troubleshooting

### "Access Denied" Error

**Solution:**
```sql
GRANT ALL PRIVILEGES ON users_accounts_application.* TO 'example_user'@'localhost';
FLUSH PRIVILEGES;
```

### "Database does not exist" Error

**Solution:**
```sql
CREATE DATABASE users_accounts_application;
```

### Foreign Key Constraint Errors

Make sure tables are dropped/created in the correct order. The schema.sql handles this automatically.

---

## Version History

- **v1.0** (2025-11-23) - Initial schema with all tables and relationships

---

## License

This database schema is part of the Users & Accounts Application project.
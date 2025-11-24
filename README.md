# Users & Accounts Application

A full-stack banking application built with Spring Boot, demonstrating enterprise-level user and account management with complex JPA relationships.

**Author:** Colt Warren  
**Status:** Active Development  
**Purpose:** Portfolio Project

---

## 📋 Project Overview

This application is a comprehensive banking system that manages users, their addresses, and multiple bank accounts. Built with Spring Boot and MySQL, it showcases professional software engineering practices including clean architecture, optimized database queries, and robust relationship management.

The application implements a complete CRUD system with proper entity relationships, demonstrating proficiency in backend development, database design, and modern Java frameworks.

---

## ✨ Features

### User Management
- ✅ User registration with automatic account creation
- ✅ Complete CRUD operations (Create, Read, Update, Delete)
- ✅ Automatic timestamp tracking for user creation
- ✅ Password management
- ✅ User profile management

### Account Management
- ✅ Multiple accounts per user (Checking, Savings, Investment, etc.)
- ✅ Dynamic account creation and naming
- ✅ Account editing and updates
- ✅ Many-to-Many relationship support (users can share accounts)

### Address Management
- ✅ One-to-One relationship with users
- ✅ Full address support (street, city, region, country, zip)
- ✅ Cascade operations (address deleted with user)

### Technical Features
- ✅ Optimized database queries with JOIN FETCH
- ✅ N+1 query problem prevention
- ✅ Clean logging configuration
- ✅ Professional error handling
- ✅ Responsive web interface with Thymeleaf

---

## 🛠️ Technology Stack

### Backend
- **Java 11** - Core programming language
- **Spring Boot 2.2.5** - Application framework
- **Spring Data JPA** - Data persistence
- **Hibernate 5.4.12** - ORM framework
- **Spring MVC** - Web layer

### Frontend
- **Thymeleaf** - Template engine
- **HTML5/CSS3** - Markup and styling
- **Bootstrap** - Responsive design

### Database
- **MySQL 8.0** - Relational database
- **HikariCP** - Connection pooling

### Build & Development
- **Maven** - Dependency management
- **IntelliJ IDEA** - IDE
- **MySQL Workbench** - Database management

---

## 🗄️ Database Schema

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

### Tables

#### `users`
| Column | Type | Description |
|--------|------|-------------|
| user_id | BIGINT (PK) | Auto-generated user ID |
| username | VARCHAR(255) | Unique username |
| password | VARCHAR(255) | User password |
| name | VARCHAR(255) | Full name |
| created_date | DATE | Account creation date |

#### `addresses`
| Column | Type | Description |
|--------|------|-------------|
| user_id | BIGINT (PK, FK) | References users.user_id |
| address_line1 | VARCHAR(255) | Primary address line |
| address_line2 | VARCHAR(255) | Secondary address line |
| city | VARCHAR(255) | City |
| region | VARCHAR(255) | State/Province |
| country | VARCHAR(255) | Country |
| zip_code | VARCHAR(255) | Postal code |

#### `accounts`
| Column | Type | Description |
|--------|------|-------------|
| account_id | BIGINT (PK) | Auto-generated account ID |
| account_name | VARCHAR(255) | Account name/type |

#### `user_account` (Join Table)
| Column | Type | Description |
|--------|------|-------------|
| user_id | BIGINT (FK) | References users.user_id |
| account_id | BIGINT (FK) | References accounts.account_id |

#### `transactions`
| Column | Type | Description |
|--------|------|-------------|
| transaction_id | BIGINT (PK) | Auto-generated transaction ID |
| amount | DECIMAL | Transaction amount |
| transaction_date | TIMESTAMP | When transaction occurred |
| account_id | BIGINT (FK) | References accounts.account_id |

---

## 🚀 Setup Instructions

### Prerequisites
- Java 11 or higher
- Maven 3.6+
- MySQL 8.0+
- Git

### Database Setup

1. **Create the database:**
```sql
CREATE DATABASE users_accounts_application;
```

2. **Create database user:**
```sql
CREATE USER 'example_user'@'localhost' IDENTIFIED BY 'password123';
GRANT ALL PRIVILEGES ON users_accounts_application.* TO 'example_user'@'localhost';
FLUSH PRIVILEGES;
```

### Application Setup

1. **Clone the repository:**
```bash
git clone https://github.com/yourusername/Users-Accounts-Application.git
cd Users-Accounts-Application
```

2. **Configure database connection:**

Edit `src/main/resources/application.properties` if needed:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/users_accounts_application
spring.datasource.username=example_user
spring.datasource.password=password123
```

3. **Build the project:**
```bash
mvn clean install
```

4. **Run the application:**
```bash
mvn spring-boot:run
```

5. **Access the application:**
```
http://localhost:8080/users
```

---

## 🌐 API Endpoints

### User Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/users` | List all users |
| GET | `/register` | User registration form |
| POST | `/register` | Create new user |
| GET | `/users/{userId}` | View user details |
| POST | `/users/{userId}` | Update user information |
| POST | `/users/{userId}/delete` | Delete user |

### Account Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/users/{userId}/accounts/{accountId}` | View account details |
| POST | `/users/{userId}/accounts` | Create new account |
| POST | `/users/{userId}/accounts/{accountId}` | Update account |

---

## 📁 Project Structure
```
Users-Accounts-Application/
├── src/main/java/com/coderscampus/assignment13/
│   ├── Assignment13Application.java
│   ├── domain/
│   │   ├── User.java
│   │   ├── Address.java
│   │   ├── Account.java
│   │   └── Transaction.java
│   ├── repository/
│   │   ├── UserRepository.java
│   │   ├── AddressRepository.java
│   │   ├── AccountRepository.java
│   │   └── TransactionRepository.java
│   ├── service/
│   │   ├── UserService.java
│   │   ├── AddressService.java
│   │   ├── AccountService.java
│   │   └── TransactionService.java
│   └── web/
│       └── UserController.java
├── src/main/resources/
│   ├── templates/
│   │   ├── users.html
│   │   ├── register.html
│   │   └── account.html
│   └── application.properties
└── pom.xml
```

---

## 🎯 Key Technical Implementations

### 1. Optimized Database Queries
```java
@Query("SELECT u FROM User u LEFT JOIN FETCH u.accounts LEFT JOIN FETCH u.address")
List findAllUsersWithAccountsAndAddresses();
```
Uses JOIN FETCH to prevent N+1 query problems and improve performance.

### 2. Relationship Management
- **One-to-One:** User ↔ Address (using @MapsId for shared primary key)
- **One-to-Many:** Account → Transactions
- **Many-to-Many:** User ↔ Account (with join table)

### 3. Automatic Timestamp Generation
```java
@CreationTimestamp
public LocalDate getCreatedDate() {
    return createdDate;
}
```
Automatically sets creation date when user is registered.

### 4. Clean Architecture
- **Controller Layer:** Handles HTTP requests and responses
- **Service Layer:** Contains business logic
- **Repository Layer:** Database access
- **Domain Layer:** Entity models

---

## 🔧 Configuration Highlights

### Clean Console Logging
```properties
spring.jpa.show-sql=false
```
Configured for production-ready, clean console output without SQL spam.

### Auto Schema Management
```properties
spring.jpa.hibernate.ddl-auto=update
```
Automatically creates and updates database schema based on entity changes.

### Java 24 Compatibility
```
VM Options: --add-opens java.base/java.lang=ALL-UNNAMED --enable-native-access=ALL-UNNAMED
```
Configured for compatibility with latest Java versions.

---

## 🧪 Testing

The application has been thoroughly tested with:
- ✅ User CRUD operations
- ✅ Account creation and management
- ✅ Address updates
- ✅ Cascade delete operations
- ✅ Database persistence verification
- ✅ Relationship integrity testing

---

## 🚀 Future Enhancements

### Planned Features
- [ ] Transaction history display
- [ ] Deposit and withdrawal functionality
- [ ] Account balance tracking
- [ ] Transfer money between accounts
- [ ] Input validation with @Valid annotations
- [ ] Spring Security authentication
- [ ] Password encryption
- [ ] RESTful API endpoints
- [ ] Unit and integration tests
- [ ] Account statements/reports

### Technical Improvements
- [ ] Implement pagination for user list
- [ ] Add search functionality
- [ ] Enhanced error handling
- [ ] Email notifications
- [ ] Audit logging
- [ ] Docker containerization

---

## 📚 Learning Outcomes

This project demonstrates proficiency in:
- ✅ Spring Boot application development
- ✅ JPA/Hibernate ORM mapping
- ✅ Complex entity relationships
- ✅ Database schema design
- ✅ Query optimization
- ✅ MVC architecture
- ✅ Template-based UI development
- ✅ RESTful endpoint design
- ✅ Maven project management
- ✅ MySQL database administration

---

## 👤 Author

**Colt Warren**

- Portfolio: [Your Portfolio URL]
- GitHub: [https://github.com/ColtWarren)
- LinkedIn: [linkedin.com/in/colt-warren-7828002b1)

---

## 🙏 Acknowledgments

- Built as a portfolio project to demonstrate full-stack development skills
- Special thanks to the Spring Boot and Hibernate communities for excellent documentation
- Inspired by real-world banking application requirements

---

**⭐ If you found this project helpful, please consider giving it a star!**

package com.coderscampus.assignment13.web;

import com.coderscampus.assignment13.domain.Account;
import com.coderscampus.assignment13.domain.Address;
import com.coderscampus.assignment13.domain.User;
import com.coderscampus.assignment13.service.AccountService;
import com.coderscampus.assignment13.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Arrays;
import java.util.Set;

@Controller
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private AccountService accountService;

    @GetMapping("/register")
    public String getCreateUser(ModelMap model) {
        model.put("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String postCreateUser(User user) {
        logger.info("Registering new user: {}", user.getUsername());
        User savedUser = userService.saveUser(user);

        if (savedUser != null && savedUser.getUserId() != null) {
            logger.info("User registered successfully with ID: {}", savedUser.getUserId());
            return "redirect:/users/" + savedUser.getUserId();
        }

        logger.warn("Failed to register user: {}", user.getUsername());
        return "redirect:/users";
    }

    @GetMapping("/users")
    public String getAllUsers(ModelMap model) {
        Set<User> users = userService.findAll();
        model.put("users", users);

        if (users.size() == 1) {
            model.put("user", users.iterator().next());
        }

        return "users";
    }

    @GetMapping("/users/{userId}")
    public String getOneUser(ModelMap model, @PathVariable Long userId) {
        User user = userService.findById(userId);

        // Ensure address exists (initialize if null)
        if (user.getAddress() == null) {
            Address newAddress = new Address();
            newAddress.setUser(user);
            newAddress.setUserId(user.getUserId());
            user.setAddress(newAddress);
        }

        model.put("users", Arrays.asList(user));
        model.put("user", user);

        return "register";
    }

    @PostMapping("/users/{userId}")
    public String postOneUser(User user) {
        logger.info("Updating user: {}", user.getUserId());

        if (user.getAddress() != null) {
            user.getAddress().setUserId(user.getUserId());
            user.getAddress().setUser(user);
        }

        userService.saveUser(user);
        logger.info("User updated successfully: {}", user.getUserId());

        return "redirect:/users/" + user.getUserId();
    }

    @PostMapping("/users/{userId}/delete")
    public String deleteOneUser(@PathVariable Long userId) {
        logger.info("Deleting user: {}", userId);
        userService.delete(userId);
        logger.info("User deleted successfully: {}", userId);

        return "redirect:/users";
    }

    @GetMapping("/users/{userId}/accounts/{accountId}")
    public String getAccount(ModelMap model, @PathVariable Long userId, @PathVariable Long accountId) {
        Account account = accountService.findById(accountId);
        model.put("account", account);
        model.put("userId", userId);

        return "account";
    }

    @PostMapping("/users/{userId}/accounts")
    public String createAccount(@PathVariable Long userId) {
        logger.info("Creating new account for user: {}", userId);
        Account newAccount = accountService.createAccountForUser(userId);

        if (newAccount != null) {
            logger.info("Account created successfully: {}", newAccount.getAccountId());
            return "redirect:/users/" + userId + "/accounts/" + newAccount.getAccountId();
        }

        logger.warn("Failed to create account for user: {}", userId);
        return "redirect:/users/" + userId;
    }

    @PostMapping("/users/{userId}/accounts/{accountId}")
    public String updateAccount(Account account, @PathVariable Long userId, @PathVariable Long accountId) {
        logger.info("Updating account: {}", accountId);

        account.setAccountId(accountId);
        accountService.saveAccount(account);

        logger.info("Account updated successfully: {}", accountId);
        return "redirect:/users/" + userId;
    }
}
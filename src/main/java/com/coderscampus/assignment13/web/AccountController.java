package com.coderscampus.assignment13.web;

import com.coderscampus.assignment13.domain.Account;
import com.coderscampus.assignment13.domain.User;
import com.coderscampus.assignment13.service.AccountService;
import com.coderscampus.assignment13.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class AccountController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private UserService userService;
    @GetMapping("/users/{userId}/accounts/new")
    public String newAccount (@PathVariable Long userId, ModelMap map) {
        User user= userService.findById(userId);
        Account account = new Account();
        // refinery needed
        account.setAccountName(user.getName()+"is"+user.getAccounts().size());
        account.getUsers().add(user);
        user.getAccounts().add(account);
        map.put("user",user);
        map.put("account",account);
        return "account";
    }

    @PostMapping("/users/{userId}/accounts/new")
    public String postCreateAccount(Account account, @PathVariable Long userId) {
        User user = userService.findById(userId);
        account.setAccountName(account.getAccountName());
        account.getUsers().add(user);
        user.getAccounts().add(account);
        accountService.saveAccount(account);
        userService.saveUser(user);
        return "redirect:/users/" + userId;
    }

    @GetMapping("/users/{userId}/accounts/{accountId}")
    public String createNewAccount (@PathVariable Long userId,@PathVariable Long accountId, ModelMap map) {

        User user= userService.findById(userId);
        Account account =accountService.findById(accountId);
        map.put("user",user);
        map.put("account",account);
        return "accountupdate";
    }
    @PostMapping("/users/{userId}/accounts/{accountId}")
    public String saveNewAccount (@PathVariable Long userId, @PathVariable Long accountId, ModelMap map, @RequestParam("accountName") String newAccountName) {
        Account account =accountService.findById(accountId);
        account.setAccountName(newAccountName);
        accountService.saveAccount(account);
        return "redirect:/users/" + userId;
    }


}

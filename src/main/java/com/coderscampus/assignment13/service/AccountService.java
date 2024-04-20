package com.coderscampus.assignment13.service;

import com.coderscampus.assignment13.domain.Account;
import com.coderscampus.assignment13.domain.Address;
import com.coderscampus.assignment13.domain.User;
import com.coderscampus.assignment13.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    @Autowired
    AccountRepository accountRepository;
    public Account saveAccount(Account account){
        return accountRepository.save(account);
    }
    public Account testSaveAccount(Account account, User user){

           /* Account newaccount = new Account();
            // adding user to account and vice versa adding account to user
            newaccount.getUsers().add(user);
            user.getAccounts().add(newaccount);*/

        return accountRepository.save(account);
    }
}

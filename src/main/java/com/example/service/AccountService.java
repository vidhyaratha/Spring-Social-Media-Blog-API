package com.example.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.entity.Account;
import com.example.repository.AccountRepository;
import com.example.exception.*;


@Service
public class AccountService {

    public AccountRepository accountRepository;

    // Injecting the AccountRepository dependency into this class through Constructor
    @Autowired
    public AccountService(AccountRepository accountRepository)
    {
        this.accountRepository = accountRepository;
    }



    public Account createNewAccount(Account newAccount) throws DuplicateUsernameException, BadRequestException
    {
        //Retrieving the user with the username to check if the account with this username already exists
        Optional<Account> existingAccount = accountRepository.findByUsername(newAccount.getUsername()); 
        
        //If the username is blank or if the password is less than 4 characters, it will throw an exception which will be handled by the @ExceptionHandler in the Controller class
        if(newAccount.getUsername().isBlank() || newAccount.getPassword().length() < 4)
        {
            throw new BadRequestException("Username and password cannot be blank and Password should be atleast 4 characters long");
        }
        // If the existing Account is not empty, it throws an exception which will be handles by the @ExceptionHandler
        if(!(existingAccount.isEmpty()))
        {
            throw new DuplicateUsernameException("Username already exists");
        }
        // Persists the data to the database and returns it
        return accountRepository.save(newAccount);
    }
}

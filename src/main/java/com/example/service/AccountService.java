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


    // Creates a new account after validating the provided account details
    public Account createNewAccount(Account newAccount) 
    {
        // Retrieving the account with the username to check if the account with the provided username already exists
        Optional<Account> existingAccount = accountRepository.findByUsername(newAccount.getUsername()); 
        
        /*  If the username is blank or if the password is less than 4 characters, 
            it will throw a BadRequestException which will be handled by the @ExceptionHandler in the Controller class
        */
        if(newAccount.getUsername().isBlank() || newAccount.getPassword().length() < 4)
        {
            throw new BadRequestException("Username and password cannot be blank and Password should be atleast 4 characters long");
        }
        // If the existing Account is not empty, it throws a DuplicateUsernameException which will be handled by the @ExceptionHandler
        if(!(existingAccount.isEmpty()))
        {
            throw new DuplicateUsernameException("Username already exists");
        }
        // Persists the data to the database and returns it
        return accountRepository.save(newAccount);
    }





    // Logs into the account after validating the account credentials
    public Account verifyLogin(Account loginAccount)
    {
        // Retrieving the account with the username to check if the account with the provided username already exists
        Optional<Account> existingAccount = accountRepository.findByUsername(loginAccount.getUsername());

        /*  If the existing  account is present and the provided username and password matches with the existing account username and password, on successful login,
            it retrieves the account using the username and returns it
        */
        if(existingAccount.isPresent() && ((existingAccount.get().getUsername().equals(loginAccount.getUsername())) && (existingAccount.get().getPassword().equals(loginAccount.getPassword()))))
        {
            return accountRepository.findByUsername(loginAccount.getUsername()).get();
            
        }
        //  If the existing account is not present or if  it doesn't match, it throws an Unauthorized Exception which will be handled by the @ExceptionHandler
        throw new UnauthorizedException("Account with username/password does not exist. Please enter the valid credentials");
    }


  
}

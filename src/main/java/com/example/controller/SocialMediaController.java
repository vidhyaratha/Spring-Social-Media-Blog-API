package com.example.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.example.entity.Account;
import com.example.exception.BadRequestException;
import com.example.exception.DuplicateUsernameException;
import com.example.service.AccountService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {

    public AccountService accountService;

    // Injecting the AccountService dependency into this class through Constructor
    @Autowired
    public SocialMediaController(AccountService accountService){
        this.accountService = accountService;
    }



    // Post Handler method to register a new user

    @PostMapping("/register")
    public ResponseEntity<Account> UserRegistration(@RequestBody Account newAccount) throws DuplicateUsernameException, BadRequestException
    {
        Account account = accountService.createNewAccount(newAccount);
        return ResponseEntity.ok(account);
    }






    // Handles the exceptions thrown by the Service 

    @ExceptionHandler(DuplicateUsernameException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleDuplicateUsername(DuplicateUsernameException ex)
    // public ResponseEntity<String> handleDuplicateUsername(DuplicateUsernameException ex)
    {
       //return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already exists" );
       return " Username already exists " + ex.getMessage();
    }


    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handlebadRequest(BadRequestException ex)
    {
        return " Registration not successful " + ex.getMessage();
    }
}

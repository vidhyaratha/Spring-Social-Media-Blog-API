package com.example.controller;

import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.example.entity.Account;
import com.example.entity.Message;
import com.example.exception.BadRequestException;
import com.example.exception.DuplicateUsernameException;
import com.example.exception.UnauthorizedException;
import com.example.service.AccountService;
import com.example.service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {

    private final AccountService accountService;

    private final MessageService messageService;

    //Injecting the AccountService, MessageService dependencies into this class through Constructor using @Autowired
    @Autowired
    public SocialMediaController(AccountService accountService, MessageService messageService){
        this.accountService = accountService;
        this.messageService = messageService;
    }





    // Post Method Handler method to register a new user

    @PostMapping("/register")
    public ResponseEntity<Account> userRegistration(@RequestBody Account newAccount) 
    {
        Account registeredAccount = accountService.createNewAccount(newAccount);
        return ResponseEntity.ok(registeredAccount);
    }




    // Post Method handler to login a user to his/her account

    @PostMapping("/login")
    public ResponseEntity<Account> login(@RequestBody Account loginAccount) 
    {
        Account loggedInAccount = accountService.verifyLogin(loginAccount);
        return ResponseEntity.ok(loggedInAccount);
    }





    // Post Method handler to create a new message

    @PostMapping("/messages")
    public ResponseEntity<Message> createNewMessage(@RequestBody Message newMessage)
    {
        Message createdMessage = messageService.validateUserAndCreateNewMessage(newMessage);
        return ResponseEntity.ok(createdMessage);
    }






    //  Get Method Handler to retrieve all messages
    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getAllMessages()
    {
        List<Message> allMessages = messageService.retrieveAllMessages();
        return ResponseEntity.ok(allMessages);
    }



    



    //   Get Method Handler to retrieve a message by message id
    @GetMapping("/messages/{message_id}")
    public ResponseEntity<Message> getOneMessageGivenMessageId(@PathVariable("message_id") int msgId)
    {
        Message retrievedMessage = messageService.retrieveMessageById(msgId);
        return ResponseEntity.ok(retrievedMessage);
    }







    //  Delete Method Handler to dleete a message by message id
    @DeleteMapping("/messages/{message_id}")
    public ResponseEntity<Integer> deleteAMessageGivenMessageId(@PathVariable("message_id") int msgId)
    {
        int deletedMessageCount = messageService.deleteMessagebyMessageId(msgId);
        if(deletedMessageCount == 0)
        {
            return ResponseEntity.status(HttpStatus.OK).body(null);
        }
        return ResponseEntity.ok(deletedMessageCount);
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


    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String handleUnauthorised(UnauthorizedException ex)
    {
        return "Login not successful" + ex.getMessage();
    }
}

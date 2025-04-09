package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.exception.BadRequestException;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;

@Service
public class MessageService {

    private final MessageRepository messageRepository;

    private final AccountRepository accountRepository;


    //  Injecting the MessageRepository and AccountRepository dependencies into this class through Constructor using @Autowired
    @Autowired
    public MessageService(MessageRepository messageRepository, AccountRepository accountRepository)
    {
        this.messageRepository = messageRepository;
        this.accountRepository =accountRepository;
    }






    //  Creates a new message after validating the user
    public Message validateUserAndCreateNewMessage(Message newMessage)
    {   
        //  Validating the User Account by retrieving the accound by Id
        Optional<Account> existingAccount = accountRepository.findById(newMessage.getPostedBy());
        //  If the useraccount exists and the message field is not blank and is within 255 characters, it gets saved to the database.
        if(existingAccount.isPresent() && (!newMessage.getMessageText().isBlank())  && (newMessage.getMessageText().length() < 255))
        {
            return messageRepository.save(newMessage);
        }
        //  If the user account is not present, it throws a BadRequestException which will be handled by the @ExceptionHandler
        throw new BadRequestException("User is not found in the database and hence cannot create a message / Message Creation is unsuccessful");
    }







    //  Retrieves all the messages from the database and returns the list, even if the list is empty
    public List<Message> retrieveAllMessages()
    {
        return messageRepository.findAll();
    }



}

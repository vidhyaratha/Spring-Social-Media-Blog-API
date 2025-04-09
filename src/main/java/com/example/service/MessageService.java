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






    //  Retrieves the message by message id and returns it even if there is no message/if it's empty/if it's null
    public Message retrieveMessageById(int messageId)
    {
        Optional<Message> retrievedMessage = messageRepository.findById(messageId);
        return retrievedMessage.orElse(null);
    }







    //   Deletes the message by message id and returns the number of rows that are deleted
    public int deleteMessagebyId(int messageId)
    {
        //  If the message with the message id exists in the database, it gets deleted and returns the number of rows deleted as 1 , else returns 0
        boolean messageExists = messageRepository.existsById(messageId); 
        if(messageExists)
        {
           messageRepository.deleteById(messageId);
           return 1;
        }
        return 0;
    }







    //   Updates the  existing message with the new text message by message id 
    public int updateMessageById(int msgId, Message newTextMsg)
    {
        /*  If the message with the message id exists in the database and if the length of new text message is less than 255 characters and is not blank,
            then the update will be successfull and returns the nums of rows updated as 1, else throws a BadRequestException which will be handled by the @ExceptionHandler
        */
        boolean msgExists = messageRepository.existsById(msgId);
        if(msgExists  &&  newTextMsg.getMessageText().length() <= 255  &&  !newTextMsg.getMessageText().isBlank())
        {
            Message messageToBeUpdated = messageRepository.findById(msgId).get();
            messageToBeUpdated.setMessageText(newTextMsg.getMessageText());
            messageRepository.save(messageToBeUpdated);
            return 1;
        }
        if(!msgExists)
        {
            throw new BadRequestException("Message Id does not exist");
        }
        if(newTextMsg.getMessageText().length() > 255)
        {
            throw new BadRequestException("New Message text cannot be more than 255 characters");
        }
        if(newTextMsg.getMessageText().isBlank())
        {
            throw new BadRequestException(" New Text Message cannot be blank");
        }
        throw new BadRequestException("Update is unsuccessfull");
        }
       







        //   Retrieves all the messages from the given account id
        public List<Message> retrieveAllMessagesByAccountId(int accountId)
        {
            List<Message> messagesByAccountId = messageRepository.findByPostedBy(accountId);
            return messagesByAccountId;
        }
    
}

package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.entity.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {

     // Used Spring Data JPA's property expression to retrieve the messages by postedBy property
     public List<Message> findByPostedBy(int id);
}

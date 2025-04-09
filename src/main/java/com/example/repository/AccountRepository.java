package com.example.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.entity.Account;


@Repository
public interface AccountRepository extends JpaRepository<Account,Integer> {

    // Used property expression to retrieve the user account by username property of Account 
    public Optional<Account> findByUsername(String username);


}

package com.example.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.entity.Account;
import org.springframework.data.repository.query.Param;


@Repository
public interface AccountRepository extends JpaRepository<Account,Integer> {

    // Used property expression to retrieve the user by username
    public Optional<Account> findByUsername(@Param("uname") String username);


}

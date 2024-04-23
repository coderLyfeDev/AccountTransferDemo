package com.fhlBank.demo.repositories;

import com.fhlBank.demo.entities.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {

    @Query("SELECT t FROM TransactionEntity t WHERE t.toAccount = :accountNumber OR t.fromAccount = :accountNumber")
    List<TransactionEntity> findAllByToAccountOrFromAccount(int accountNumber);


    List<TransactionEntity> findAllByFromAccount(int accountNumber);

}

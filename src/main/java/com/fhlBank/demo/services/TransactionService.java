package com.fhlBank.demo.services;

import com.fhlBank.demo.entities.TransactionEntity;
import com.fhlBank.demo.requests.TransactionRequest;
import com.fhlBank.demo.responses.Response;

import java.util.List;

public interface TransactionService {

    List<TransactionEntity> findAllByAccountNumber(int accountNumber);

    List<TransactionEntity> findAllTransactions();

    Response createTransaction(TransactionRequest request);

    Response validateTransaction(TransactionRequest request);

}

package com.fhlBank.demo.controllers;

import com.fhlBank.demo.entities.TransactionEntity;
import com.fhlBank.demo.requests.TransactionRequest;
import com.fhlBank.demo.responses.Response;
import com.fhlBank.demo.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
    @Autowired
    TransactionService transactionService;

    @GetMapping("/get/byAccountNumber")
    public ResponseEntity<List<TransactionEntity>> getTransactionsByAccountNumber(@RequestParam int accountNumber){
        List<TransactionEntity> transactions = transactionService.findAllByAccountNumber(accountNumber);
        return ResponseEntity.status(HttpStatus.OK)
                .body(transactions);
    }

    @GetMapping("/get/all")
    public ResponseEntity<List<TransactionEntity>> getAllTransactions(){
        List<TransactionEntity> transactions = transactionService.findAllTransactions();
        return ResponseEntity.status(HttpStatus.OK)
                .body(transactions);
    }


    @PostMapping("/create/transaction")
    public ResponseEntity<Response> createTransaction(@RequestBody TransactionRequest request){
            Response response = transactionService.createTransaction(request);;
        return ResponseEntity.status(HttpStatus.OK)
                .body(response);


    }
}

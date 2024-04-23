package com.fhlBank.demo.services;

import com.fhlBank.demo.entities.CustomerEntity;
import com.fhlBank.demo.entities.TransactionEntity;
import com.fhlBank.demo.repositories.CustomerRepository;
import com.fhlBank.demo.repositories.TransactionRepository;
import com.fhlBank.demo.requests.TransactionRequest;
import com.fhlBank.demo.responses.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.sql.Timestamp;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    CustomerRepository customerRepository;

    final static double maxTransferAmount = 1000000.0;
    public List<TransactionEntity> findAllByAccountNumber(int accountNumber) {
        return transactionRepository.findAllByToAccountOrFromAccount(accountNumber);
    }

    public List<TransactionEntity> findAllTransactions(){
        return transactionRepository.findAll();
    }

    public Response createTransaction(TransactionRequest request){
        Response response = validateTransaction(request);
        if(response.getHttpStatus() == HttpStatus.OK) {
            TransactionEntity transactionEntity = new TransactionEntity();
            transactionEntity.setToAccount(request.getToAccount());
            transactionEntity.setFromAccount(request.getFromAccount());
            transactionEntity.setTransferAmount(request.getAmount());
            transactionEntity.setCreated(new Timestamp(System.currentTimeMillis()));

            transactionRepository.save(transactionEntity);
            updateBalance(request.getToAccount(), request.getFromAccount(), request.getAmount());
        }else{
            return response;
        }
        return new Response("Transaction has been initiated", HttpStatus.OK);
    }


    private void updateBalance(int toAccount, int fromAccount, double amount){
        CustomerEntity withdralAccount = customerRepository.findByAccountNumber(fromAccount).get(0);
        CustomerEntity depositAccount = customerRepository.findByAccountNumber(toAccount).get(0);

        withdralAccount.setBalance(withdralAccount.getBalance()-amount);
        customerRepository.save(withdralAccount);
        depositAccount.setBalance(depositAccount.getBalance()+amount);
        customerRepository.save(depositAccount);
    }

    public Response validateTransaction(TransactionRequest request){
        List<TransactionEntity> transactions = transactionRepository.findAllByFromAccount(request.getFromAccount());
        double sumTransactions = 0.0;

        for(TransactionEntity t : transactions){
            sumTransactions += t.getTransferAmount();
        }
        if(sumTransactions+request.getAmount() > maxTransferAmount) return new Response("Account: "+request.getFromAccount() +" has exceeded the max transfer limit of "+maxTransferAmount, HttpStatus.BAD_REQUEST);
        if(request.getToAccount() == request.getFromAccount()) return new Response("To and from Account cannot be the same", HttpStatus.BAD_REQUEST);
        if(request.getAmount() <= 0.00) return new Response("Transfer Amount must be greater than 0", HttpStatus.BAD_REQUEST);

        List<CustomerEntity> withdralAccount = customerRepository.findByAccountNumber(request.getFromAccount());
        List<CustomerEntity> depositAccount = customerRepository.findByAccountNumber(request.getToAccount());
        if(withdralAccount.size() == 0 || depositAccount.size() == 0) return new Response("To or from Account is invalid", HttpStatus.BAD_REQUEST);
        if(withdralAccount.get(0).getBalance() < request.getAmount()) return new Response("Not enough funds in the from account", HttpStatus.BAD_REQUEST);
        return new Response("Transaction has been initiated", HttpStatus.OK);

    }
}

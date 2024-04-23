package com.fhlBank.demo.services;

import com.fhlBank.demo.entities.CustomerEntity;
import com.fhlBank.demo.entities.TransactionEntity;
import com.fhlBank.demo.repositories.CustomerRepository;
import com.fhlBank.demo.repositories.TransactionRepository;
import com.fhlBank.demo.requests.CustomerRequest;
import com.fhlBank.demo.requests.TransactionRequest;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class CustomerServiceImplTest {

    @Autowired
    CustomerServiceImpl customerService;

    @Autowired
    TransactionServiceImpl transactionService;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    TransactionRepository transactionRepository;

    @Test
    public void testCreateCustomer(){
        customerService.createCustomer(new CustomerRequest("Brandon", "Harshaw", "Checking",2000.0));
        customerService.createCustomer(new CustomerRequest("Tony", "Romo", "Checking",0.0));
        List<CustomerEntity> foundCustomers = customerRepository.findAll();
        assertEquals(2,foundCustomers.size());
    }

    @Test
    public void testCreateTransaction(){
        CustomerEntity customer1 = customerService.createCustomer(new CustomerRequest("Brandon", "Harshaw", "Checking",2000.0));
        CustomerEntity customer2 = customerService.createCustomer(new CustomerRequest("Tony", "Romo", "Checking",0.0));
        TransactionRequest transactionRequest = new TransactionRequest(customer2.getAccountNumber(), customer1.getAccountNumber(), 250);
        transactionService.createTransaction(transactionRequest);
        List<TransactionEntity> transactions = transactionRepository.findAll();
        assertEquals(1,transactions.size());
        assertEquals(250.0,transactions.get(0).getTransferAmount());
        customer1 = customerService.findById(customer1.getId());
        customer2 = customerService.findById(customer2.getId());
        assertEquals(1750.0,customer1.getBalance());
        assertEquals(250.0, customer2.getBalance());
    }

}

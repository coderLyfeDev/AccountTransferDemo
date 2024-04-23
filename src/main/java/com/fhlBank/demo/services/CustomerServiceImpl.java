package com.fhlBank.demo.services;

import com.fhlBank.demo.entities.CustomerEntity;
import com.fhlBank.demo.repositories.CustomerRepository;
import com.fhlBank.demo.requests.CustomerRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    public CustomerEntity findById(Long id) {
        return customerRepository.findById(id).get();
    }

    public List<CustomerEntity> findAll(){
        return customerRepository.findAll();
    }

    public CustomerEntity createCustomer(CustomerRequest request){
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setFirstName(request.getFirstName());
        customerEntity.setLastName(request.getLastName());
        if(request.getDepositAmount() != null){
            customerEntity.setBalance(request.getDepositAmount());
        }else{
            customerEntity.setBalance(0.00);
        }
        customerEntity.setAccountType(request.getAccountType());
        customerEntity.setAccountNumber(generateAccountNumber());
        return customerRepository.save(customerEntity);
    }

    private int generateAccountNumber(){
        Random random = new Random();
        int accountNumber = 100000000 + random.nextInt(900000000);
        List<CustomerEntity> customers = customerRepository.findByAccountNumber(accountNumber);
        if(customers.size() >0){
            return generateAccountNumber();
        }else{
            return accountNumber;
        }
    }
}

package com.fhlBank.demo.services;

import com.fhlBank.demo.entities.CustomerEntity;
import com.fhlBank.demo.requests.CustomerRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CustomerService {

    CustomerEntity findById(Long id);

    CustomerEntity createCustomer(CustomerRequest request);

    List<CustomerEntity> findAll();

}

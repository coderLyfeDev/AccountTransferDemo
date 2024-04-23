package com.fhlBank.demo.controllers;

import com.fhlBank.demo.entities.CustomerEntity;
import com.fhlBank.demo.requests.CustomerRequest;
import com.fhlBank.demo.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @GetMapping("get/byId")
    public ResponseEntity<CustomerEntity> getCustomer(Long id){
        CustomerEntity customerEntity = customerService.findById(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(customerEntity);
    }

    @PostMapping("/create/customer")
    public ResponseEntity<CustomerEntity> createCustomer(@RequestBody CustomerRequest request){
        CustomerEntity customerEntity = customerService.createCustomer(request);
        return ResponseEntity.status(HttpStatus.OK)
                .body(customerEntity);
    }

    @GetMapping("get/customers")
    public ResponseEntity<List<CustomerEntity>> getCustomers(){
        List<CustomerEntity> customers = customerService.findAll();
        return ResponseEntity.status(HttpStatus.OK)
                .body(customers);
    }
}

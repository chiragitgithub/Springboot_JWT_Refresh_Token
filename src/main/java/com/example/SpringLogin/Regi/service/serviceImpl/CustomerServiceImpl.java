package com.example.SpringLogin.Regi.service.serviceImpl;

import com.example.SpringLogin.Regi.entity.Customer;
import com.example.SpringLogin.Regi.repo.CustomerRepository;
import com.example.SpringLogin.Regi.service.CustomerService;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepo;
    private final PasswordEncoder passwordEncoder;


    public CustomerServiceImpl(CustomerRepository customerRepo, PasswordEncoder passwordEncoder) {
        this.customerRepo = customerRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void registerCustomer(Customer customer) {
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        customerRepo.save(customer);
    }


}

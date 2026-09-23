package com.example.bookingservice.service;


import com.example.bookingservice.dto.CustomerDto;
import com.example.bookingservice.dto.CustomerFullDto;

import java.util.List;

public interface CustomerService {
    CustomerFullDto getCustomerFullDto(Long id);
    List<CustomerFullDto> getAllCustomers();
    CustomerDto addCustomer(String name);
    void updateCustomerName(Long id, String newName);
    CustomerDto findCustomerById(Long id);
    void deleteCustomer (Long customerId);


}

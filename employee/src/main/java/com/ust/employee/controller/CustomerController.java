package com.ust.employee.controller;

import com.ust.employee.entity.Customer;
import com.ust.employee.repository.CustomerRepository;
//import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerRepository customerRepository;

    @GetMapping("/new")
    public Customer showCustomerForm(Customer customer) {
        return customer;
    }

    @PostMapping("/add")
    public Customer addCustomer(@Valid Customer customer, BindingResult result){
        if (result.hasErrors()){
            return customer;
        }
        customerRepository.save(customer);
        return customer;
    }

    @GetMapping("/customer")
    public List<Customer> getCustomers(Model model){
        List<Customer> allCustomers = customerRepository.findAll();
        model.addAttribute("customers", allCustomers.isEmpty() ? null : allCustomers);
        return allCustomers;
    }

    @GetMapping("/edit/{id}")
    public Customer showUpdateForm(@PathVariable("id") long id, Model model) {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid customer Id:" + id));
        model.addAttribute("customer", customer);
        return customer;
    }

    @PostMapping("/update/{id}")
    public Customer updateUser(@PathVariable("id") long id, @Valid Customer customer, BindingResult result) {
        if (result.hasErrors()) {
            customer.setId(id);
            return customer;
        }
        customerRepository.save(customer);
        return customer;
    }

    @GetMapping("/delete/{id}")
    public Customer deleteUser(@PathVariable("id") long id, Model model) {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid customer Id:" + id));
        customerRepository.delete(customer);
        return customer;
    }
}
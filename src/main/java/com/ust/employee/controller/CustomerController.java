package com.ust.employee.controller;

import com.ust.employee.entity.Customer;
import com.ust.employee.repository.CustomerRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import  jakarta.validation.Valid;
import java.util.List;

@Controller
public class CustomerController {

    private final CustomerRepository customerRepository;

    // Constructor without Lombok
    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @GetMapping("/new")
    public String showCustomerForm(Model model) {
        model.addAttribute("customer", new Customer());
        return "customerForm"; // Assume there's a `customerForm.html` template
    }

    @PostMapping("/add")
    public String addCustomer(@Valid Customer customer, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("customer", customer);
            return "customerForm";
        }
        customerRepository.save(customer);
        return "redirect:/customer";
    }

    @GetMapping("/customer")
    public String getCustomers(Model model) {
        List<Customer> allCustomers = customerRepository.findAll();
        model.addAttribute("customers", allCustomers);
        return "customerList"; // Assume there's a `customerList.html` template
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid customer Id:" + id));
        model.addAttribute("customer", customer);
        return "editCustomer"; // Assume there's an `editCustomer.html` template
    }

    @PostMapping("/update/{id}")
    public String updateCustomer(@PathVariable("id") long id, @Valid Customer customer, BindingResult result, Model model) {
        if (result.hasErrors()) {
            customer.setId(id);
            model.addAttribute("customer", customer);
            return "editCustomer";
        }
        customerRepository.save(customer);
        return "redirect:/customer";
    }

    @GetMapping("/delete/{id}")
    public String deleteCustomer(@PathVariable("id") long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid customer Id:" + id));
        customerRepository.delete(customer);
        return "redirect:/customer";
    }
}

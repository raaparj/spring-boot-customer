package com.example.customer;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
public class CustomerController {

    private final CustomerRepository customerRepository;

    CustomerController(CustomerRepository repository){
        this.customerRepository = repository;
    }

    @PostMapping(path = "/add", consumes = "application/json", produces = "application/json")
    public Customer addCustomer(@RequestBody Customer customer) {
        return customerRepository.save(customer);
    }

    @PutMapping(path = "/update/{id}", produces = "application/json", params = {"street","city"})
    public Customer updateCustomerOnStreetAndCityById (@PathVariable Integer id, @RequestParam("street") String street, @RequestParam("city") String city ) throws IllegalArgumentException {
        if (customerRepository.existsByIdAllIgnoreCase(id)) {
            customerRepository.updateStreetAndCityById(street, city, id);
            return customerRepository.findCustomerById(id);
        } else {
            throw new IllegalArgumentException("No record found for ID specified");
        }
    }

    @PutMapping(path = "/update/{id}", produces = "application/json", params = {"email"})
    public Customer updateCustomerOnEmailById (@PathVariable Integer id, @RequestParam("email") String email ) throws IllegalArgumentException {
        if (customerRepository.existsByIdAllIgnoreCase(id)) {
            customerRepository.updateEmailById(email, id);
            return customerRepository.findCustomerById(id);
        } else {
            throw new IllegalArgumentException("No record found for ID specified");
        }
    }

    @DeleteMapping(path = "/delete/{id}", produces = "application/json" )
    public Customer deleteCustomerById(@PathVariable Integer id) throws IllegalArgumentException {
        Customer customer;
        if (customerRepository.existsByIdAllIgnoreCase(id)) {
            customer = customerRepository.findCustomerById(id);
            customerRepository.deleteById(id);
            return customer;
        } else {
            throw new IllegalArgumentException("No record found for ID specified");
        }
    }

    @GetMapping("/list")
    public Iterable<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    @GetMapping("/list/{id}")
    public Customer listCustomerById(@PathVariable Integer id) throws IllegalArgumentException {
        if (customerRepository.existsByIdAllIgnoreCase(id)) {
            return customerRepository.findCustomerById(id);
        } else {
            throw new IllegalArgumentException("No record found for ID specified");
        }
    }

    @GetMapping(path = "search", produces = "application/json", params = {"street","city"})
    public Iterable<Customer> findCustomerByStreetCity(@RequestParam("street") String street, @RequestParam("city") String city ){
        return customerRepository.findByStreetAndCityAllIgnoreCase(street,city);
    }

    @GetMapping(path = "search", produces = "application/json", params = {"email"})
    public Iterable<Customer> findCustomerByEmail(@RequestParam("email") String email){
        return customerRepository.findByEmailIgnoreCase(email);
    }

    @GetMapping(path = "search", produces = "application/json", params = {"firstName","lastName"})
    public Iterable<Customer> findCustomerByFirstNameAndLastName(@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName){
        return customerRepository.findByFirstNameAndLastNameAllIgnoreCase(firstName,lastName);
    }

}

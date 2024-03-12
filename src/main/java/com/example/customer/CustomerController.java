package com.example.customer;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;

import static com.example.customer.Customer.isNumeric;
import static com.example.customer.Customer.isValidEmailAddress;


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

    @PutMapping(path = "/update/{id}", consumes = "application/json", produces = "application/json")
    public Customer updateCustomerById (@PathVariable Integer id, @RequestBody Customer customer ) throws IllegalArgumentException {
        if (customerRepository.existsByIdAllIgnoreCase(id)) {
            Customer repositoryCustomer = customerRepository.findCustomerById(id);
            customerRepository.updateFirstNameAndLastNameAndAgeAndStreetAndCityAndEmailById(
                    customer.getFirstName() == null ? repositoryCustomer.getFirstName(): customer.getFirstName(),
                    customer.getLastName() == null ? repositoryCustomer.getLastName() : customer.getLastName(),
                    customer.getAge() == null || !isNumeric(customer.getAge())? repositoryCustomer.getAge() : customer.getAge(),
                    customer.getStreet() == null ? repositoryCustomer.getStreet() : customer.getStreet(),
                    customer.getCity() == null ? repositoryCustomer.getCity() : customer.getCity(),
                    customer.getEmail() == null || !isValidEmailAddress(customer.getEmail())? repositoryCustomer.getEmail() : customer.getEmail(),
                    id
            );
            return repositoryCustomer;
        } else {
            throw new IllegalArgumentException("No record found for ID specified");
        }
    }

    @DeleteMapping(path = "/delete/{id}", produces = "application/json" )
    public Customer deleteCustomerById(@PathVariable Integer id) throws IllegalArgumentException {
        if (customerRepository.existsByIdAllIgnoreCase(id)) {
            Customer customer = customerRepository.findCustomerById(id);
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

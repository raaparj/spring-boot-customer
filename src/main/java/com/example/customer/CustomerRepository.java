package com.example.customer;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface CustomerRepository extends CrudRepository<Customer, Integer> {

    boolean existsByIdAllIgnoreCase(Integer id);

    @Transactional
    @Modifying
    @Query("""
            update Customer c set c.firstName = ?1, c.lastName = ?2, c.age = ?3, c.street = ?4, c.city = ?5, c.email = ?6
            where c.id = ?7""")
    void updateFirstNameAndLastNameAndAgeAndStreetAndCityAndEmailById(String firstName, String lastName, String age, String street, String city, String email, Integer id);

    Iterable<Customer> findByStreetAndCityAllIgnoreCase(String street, String city);

    Iterable<Customer> findByEmailIgnoreCase(String email);

    Iterable<Customer> findByFirstNameAndLastNameAllIgnoreCase(String firstName, String lastName);

    Customer findCustomerById(Integer id);

}

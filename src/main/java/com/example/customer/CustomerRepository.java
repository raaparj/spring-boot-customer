package com.example.customer;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface CustomerRepository extends CrudRepository<Customer, Integer> {
    boolean existsByIdAllIgnoreCase(Integer id);

    @Transactional
    @Modifying
    @Query("update Customer c set c.street = ?1, c.city = ?2 where c.id = ?3")
    void updateStreetAndCityById(String street, String city, Integer id);

    @Transactional
    @Modifying
    @Query("update Customer c set c.email = ?1 where c.id = ?2")
    void updateEmailById(String email, Integer id);

    Iterable<Customer> findByStreetAndCityAllIgnoreCase(String street, String city);

    Iterable<Customer> findByEmailIgnoreCase(String email);

    Iterable<Customer> findByFirstNameAndLastNameAllIgnoreCase(String firstName, String lastName);

    Customer findCustomerById(Integer id);

    @Override
    void deleteById(Integer integer);
}

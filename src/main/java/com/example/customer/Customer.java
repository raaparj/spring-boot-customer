package com.example.customer;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.AddressException;
import java.util.Objects;

@Entity
public class Customer{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String firstName;
    private String lastName;
    private String age;
    private String street;
    private String city;
    private String email;

    public Customer() {
    }

    public Customer(String firstName, String lastName, String age, String street, String city, String email ){
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.street = street;
        this.city = city;
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        if (isNumeric(age)) {
            this.age = age;
        } else {
            this.age = "";
        }
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if(isValidEmailAddress(email)) {
            this.email = email;
        } else {
            this.email = "";
        }
    }

    static boolean isValidEmailAddress(String email) {
        boolean result = true;
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException addressException) {
            result = false;
        }
        return result;
    }

    static boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Customer customer = (Customer) object;
        return Objects.equals(id, customer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString(){
        return(
            "[ id: " + this.id + ", " +
            "firstName: " + this.firstName + ", " +
            "lastName: " + this.lastName + ", " +
            "age: " + this.age + ", " +
            "street: "  + this.street + ", " +
            "city: " + this.city + ", " +
            "email: " + this.email + "]"
        );
    }
}

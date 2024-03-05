package com.example.customer;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;


import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class CustomerApplicationIntegrationTests {

    @Autowired
    private CustomerRepository customerRepository;

    private static JSONObject createCustomerJsonObject(String ... args) {
        JSONObject customerJSONObject = new JSONObject();

        try {
            customerJSONObject.put("firstName", args[0]);
            customerJSONObject.put("lastName", args[1]);
            customerJSONObject.put("age", args[2]);
            customerJSONObject.put("street", args[3]);
            customerJSONObject.put("city", args[4]);
            customerJSONObject.put("email",args[5]);
        }
        catch (JSONException jsonException) {
            throw new RuntimeException("Cannot create JSON object");
        }
        return customerJSONObject;
    }


    @BeforeEach
    void InitializeRepositoryWithTwoRecords() {

        // Add Bilbo to the repository
        customerRepository.save(new Customer("Bilbo", "Baggings", "120", "Bag End", "Hobbiton", "bilbo.baggings@gmail.com"));

        // Add Frodo to the repository
        customerRepository.save(new Customer("Frodo", "Baggings", "40", "Bag End", "Hobbiton", "frodo.baggings@gmail.com"));

    }

    @AfterEach
    void ClearAllRecordsFromRepository() {

        // Clear repository
        customerRepository.deleteAll();

    }

    @Test
    void givenDataIsJson_whenDataIsPostedByPostForObject_thenResponseBodyIsRecordAdded() {

        // Add Samwise Gamgee to the repository using the /add endpoint
        JSONObject customerJsonObject = createCustomerJsonObject("Samwise", "Gamgee", "35", "Main road", "Hobbiton", "samwise.gamgee@gmail.com");

        // Prepare URL to update the first record using the /add endpoint
        String addCustomerUrl= "http://localhost:8080/add";

        // Set HTTP headers and HTTP entity before issuing the POST.
        HttpHeaders headers = new HttpHeaders();
        RestTemplate restTemplate = new RestTemplate();

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<String> request =
                new HttpEntity<>(customerJsonObject.toString(), headers);

        Customer customer = restTemplate.postForObject(addCustomerUrl, request, Customer.class);

        // The RestAPI call should return Samwise Gamgee as a Customer object
        assertNotNull(customer);
        assertThat(customer.getClass()).isEqualTo(Customer.class);
        assertThat(customer.getFirstName()).isEqualTo("Samwise");
        assertThat(customer.getLastName()).isEqualTo("Gamgee");

        // Check if Samwise Gamgee is added to the repository given the ID of the record returned by the RestAPI call

        Customer customerInRepo = customerRepository.findCustomerById(customer.getId());
        assertThat(customerInRepo.getFirstName()).isEqualTo("Samwise");
        assertThat(customerInRepo.getLastName()).isEqualTo("Gamgee");

    }

    @Test
    void givenRepoContainsMultipleEntries_thenResponseBodyReturnsThemWhenListed() {

        // Check number of records in the repo to be 2
        Iterable<Customer> customersInRepo = customerRepository.findAll();
        assertThat(customersInRepo).hasSize(2);

        // Get all records using the /list endpoint
        String listCustomerUrl= "http://localhost:8080/list";
        RestTemplate restTemplate = new RestTemplate();
        Customer[] customerArray = restTemplate.getForObject(listCustomerUrl, Customer[].class);

        // The result retrieved from the /list endpoint should also return 2 records
        assertThat(customerArray).hasSize(2);

    }

    @Test
    void givenRepoContainsAtLeastOneEntry_thenResponseWouldReturnTheFirstWhenDoingGetListOnIt() {

        // Check if there is at least one record in the repo and get its ID
        List<Customer> customersInRepo = (List<Customer>) customerRepository.findAll();
        assertThat(customersInRepo).hasSizeGreaterThan(0);
        Integer firstCustomerRecordId = customersInRepo.get(0).getId();

        // Retrieve the first record using the /list/{id} endpoint
        String listFirstRecordCustomerUrl= "http://localhost:8080/list/" + firstCustomerRecordId;
        RestTemplate restTemplate = new RestTemplate();
        Customer customer = restTemplate.getForObject(listFirstRecordCustomerUrl,Customer.class);

        // The record retrieved should be the first record (having record ID stored in Integer firstCustomerRecordId).
        assertNotNull(customer);
        assertThat(customer.getId()).isEqualTo(firstCustomerRecordId);

    }

    @Test
    void givenRepoContainsAtLeastOneEntry_thenResponseWouldReturnTheFirstWhenDeletingIt() {

        // Check if there is at least one record in the repo and get its ID
        List<Customer> customersInRepo = (List<Customer>) customerRepository.findAll();
        assertThat(customersInRepo).hasSizeGreaterThan(0);
        Integer firstCustomerRecordId = customersInRepo.get(0).getId();

        // Prepare URL to update the first record using the /delete/{id} endpoint
        String deleteRecordGivenIDUrl= "http://localhost:8080/delete/" + firstCustomerRecordId;

        // Set an empty HTTP header in an empty HTTP Entity before issuing the DELETE on the /delete endpoint
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<?> requestEntity = new HttpEntity<>(headers);
        HttpEntity<Customer> response = restTemplate.exchange(deleteRecordGivenIDUrl, HttpMethod.DELETE, requestEntity, Customer.class);
        Customer customer = response.getBody();

        // The record returned should be the record having record ID stored in firstCustomerRecordId.
        assertNotNull(customer);
        assertThat(customer.getId()).isEqualTo(firstCustomerRecordId);

        // Check if the record specified is no longer in to the repository given the ID of the record returned by the RestAPI call
        Customer customerInRepo = customerRepository.findCustomerById(customer.getId());
        assertNull(customerInRepo);

    }

    @Test
    void givenRepoContainsMultipleEntriesWithTheSameAddress_thenResponseWouldReturnBilboAndFrodoWhenSearchingForAddress() {

        // Get the number of records registered to a given address (street and city); should be 2
        List<Customer> customersOnOneAddress = (List<Customer>) customerRepository.findByStreetAndCityAllIgnoreCase("Bag End", "Hobbiton");
        assertThat(customersOnOneAddress).hasSize(2);

        // Get the same using the /search endpoint specifying the same address
        String searchCustomerUrl = "http://localhost:8080/search?street=Bag End&city=Hobbiton";
        RestTemplate restTemplate = new RestTemplate();
        Customer[] customerArray = restTemplate.getForObject(searchCustomerUrl, Customer[].class);

        // Check if the result also contains 2 records (Frodo Baggins and Bilbo Baggins)
        assertThat(customerArray).hasSize(2);
        List<Customer> customers = Arrays.asList(customerArray);
        assertThat(customers).filteredOn(c -> c.getLastName().equals("Baggins"));

    }

    @Test
    void givenRepoContainsBilbo_thenResponseWouldReturnBilboWhenSearchingForHisEmail() {

        // Get the number of records registered to a given email address; should be 1
        List<Customer> customersHavingGivenEmailAddressRegistered = (List<Customer>) customerRepository.findByEmailIgnoreCase("bilbo.baggings@gmail.com");
        assertThat(customersHavingGivenEmailAddressRegistered).hasSize(1);

        // Get the same using the /search endpoint specifying the same address
        String searchCustomerUrl = "http://localhost:8080/search?email=bilbo.baggings@gmail.com";
        RestTemplate restTemplate = new RestTemplate();
        Customer[] customerArray = restTemplate.getForObject(searchCustomerUrl, Customer[].class);

        // Check if the number of records returned is also 1, if it's a Customer and if Bilbo is registered on it.
        assertThat(customerArray).hasSize(1);
        List<Customer> customers = Arrays.asList(customerArray);
        assertThat(customers.get(0)).isInstanceOf(Customer.class);
        assertThat(customers.get(0).getFirstName()).isEqualTo("Bilbo");

    }

    @Test
    void givenRepoContainsAtLeastOneEntry_thenResponseWouldReturnTheFirstWithUpdateWhenDoingAnUpdateOfStreetAndCityOnRecord1() {

        // Check if there is at least one record in the repo and get its ID
        List<Customer> customersInRepo = (List<Customer>)  customerRepository.findAll();
        assertThat(customersInRepo).hasSizeGreaterThan(0);
        Integer firstCustomerRecordId = customersInRepo.get(0).getId();

        // Check the values for street and city of the first record not to be set to "Yellow Brick Road" and "Emerald City", respectively
        Customer customerBeforeUpdate = customerRepository.findCustomerById(firstCustomerRecordId);
        assertThat(customerBeforeUpdate.getStreet()).isNotEqualTo("Yellow Brick Road");
        assertThat(customerBeforeUpdate.getCity()).isNotEqualTo("Emerald City");

        // Prepare URL to update the first record using the /update/1 endpoint
        String updateFirstRecordCustomerUrl= "http://localhost:8080/update/"+ firstCustomerRecordId + "?street=Yellow Brick Road&city=Emerald City";

        // Set an empty HTTP header in an empty HTTP Entity before issuing the PUT on the /update endpoint
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<?> requestEntity = new HttpEntity<>(headers);
        HttpEntity<Customer> response = restTemplate.exchange(updateFirstRecordCustomerUrl, HttpMethod.PUT, requestEntity, Customer.class);
        Customer customer = response.getBody();

        // The record returned should be the first record (having record ID stored in firstCustomerRecordId) and have the values for "street" and "city" updated.
        assertNotNull(customer);
        assertThat(customer.getId()).isEqualTo(firstCustomerRecordId);
        assertThat(customer.getStreet()).isEqualTo("Yellow Brick Road");
        assertThat(customer.getCity()).isEqualTo("Emerald City");

        // Check the values for street and city of the first record in the repo now to be set to "Yellow Brick Road" and "Emerald City", respectively
        Customer customerAfterUpdate = customerRepository.findCustomerById(firstCustomerRecordId);
        assertThat(customerAfterUpdate.getStreet()).isEqualTo("Yellow Brick Road");
        assertThat(customerAfterUpdate.getCity()).isEqualTo("Emerald City");

    }

    @Test
    void givenRepoContainsAtLeastOneEntry_thenResponseWouldReturnTheFirstWithUpdateWhenDoingAnUpdateOfEmailOnRecord1() {

        // Check if there is at least one record in the repo and get its ID
        List<Customer> customersInRepo = (List<Customer>) customerRepository.findAll();
        assertThat(customersInRepo).hasSizeGreaterThan(0);
        Integer firstCustomerRecordId = customersInRepo.get(0).getId();

        // Check the value of the first record not to be set to "bilbo.baggings@hotmail.com"
        Customer customerBeforeUpdate = customerRepository.findCustomerById(firstCustomerRecordId);
        assertThat(customerBeforeUpdate.getEmail()).isNotEqualTo("bilbo.baggings@hotmail.com");

        // Prepare URL to update the first record using the /update/1 endpoint
        String updateFirstRecordCustomerUrl= "http://localhost:8080/update/" + firstCustomerRecordId + "?email=bilbo.baggings@hotmail.com";

        // Set an empty HTTP header in an empty HTTP Entity before issuing the PUT on the /update endpoint
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<?> requestEntity = new HttpEntity<>(headers);
        HttpEntity<Customer> response = restTemplate.exchange(updateFirstRecordCustomerUrl, HttpMethod.PUT, requestEntity, Customer.class);
        Customer customer = response.getBody();

        // The record returned should be the first record (having record ID stored in firstCustomerRecordId) and have the value for "email" updated.
        assertNotNull(customer);
        assertThat(customer.getId()).isEqualTo(firstCustomerRecordId);
        assertThat(customer.getEmail()).isEqualTo("bilbo.baggings@hotmail.com");

        // Check the value for email of the first record in the repo now to be set to "bilbo.baggings@hotmail.com"
        Customer customerAfterUpdate = customerRepository.findCustomerById(firstCustomerRecordId);
        assertThat(customerAfterUpdate.getEmail()).isEqualTo("bilbo.baggings@hotmail.com");

    }

}

## Customer application REST API specification

This is a simple Customer application to store, search, update and delete customer data.<br>
It has the following end-points available: 

#### Add a Customer

<details>
 <summary><code>POST</code> <code><b>/add</b></code></summary>

###### Adds a Customer object to the repository

The JSON object offered in the POST must specify the <i>firstName</i>, <i>lastName</i>, <i>age</i>, <i>street</i>, <i>city</i> and <i>email</i> of the customer to be registered:

<code>
&nbsp;{<br>
&nbsp;&nbsp;  "firstName": "<i>first name</i>",<br>
&nbsp;&nbsp;  "lastName": "<i>last name</i>",<br>
&nbsp;&nbsp;  "age": "<i>61</i>",<br>
&nbsp;&nbsp;  "street": "<i>street</i>",<br>
&nbsp;&nbsp;  "city": "<i>city</i>",<br>
&nbsp;&nbsp;  "email": "<i>email address</i>",<br>
&nbsp;}
</code>

Be aware of the following with respect to the values of the fields in the JSON:
* The value for the <code>age</code> field has to be a number and the <code>email</code> field has to be in a valid email address format.<br>
They will be ignored if they aren't.
* The values of the other fields are not validated.
* The values for all fields have to be String formatted.

The JSON object offered in the POST, if accepted, will be returned with addition of the record ID it is registered with.<br>
The response will be in the format as shown below, which is the same format as the JSON object offered in the POST with the record ID in addition.<br>
This record ID can be used to list, update or delete the record added.<br>

<code>
&nbsp;{<br>
&nbsp;&nbsp;  "<b>id</b>": "<i><b>id</b></i>",<br>
&nbsp;&nbsp;  "firstName": "<i>first name</i>",<br>
&nbsp;&nbsp;  "lastName": "<i>last name</i>",<br>
&nbsp;&nbsp;  "age": "<i>61</i>",<br>
&nbsp;&nbsp;  "street": "<i>street</i>",<br>
&nbsp;&nbsp;  "city": "<i>city</i>",<br>
&nbsp;&nbsp;  "email": "<i>email address</i>",<br>
&nbsp;}
</code>

Be aware that the same record (as an identical JSON object) can be added more than once and, if so, will be stored in the repository multiple times.<br>
Records will differ on the record id's, though.

##### Parameters

> | name      |  type     | data type          | description |
> |-----------|-----------|--------------------|-------------|
> | None      |  required | object (JSON)      | See above   |


##### Responses

> | http code | content-type             | response                                    |
> |-----------|--------------------------|---------------------------------------------|
> | `200`     | `application/json`       | Customer object added with record id (JSON) |
> | `400`     | `application/json`       | `Bad request`                               |


##### Example cURL

> ```curl
>  curl -X POST -H "Content-Type: application/json" --data @customer.json http://localhost:8080/add
> ```


</details>

#### Update a Customer

<details>
  <summary><code>PUT</code> <code><b>/update/{id}</b></code></summary>

###### Updates an existing record specified by its record id

In the JSON object offered in the PUT the fields to be updated, being one or more of <i>firstName</i>, <i>lastName</i>, <i>age</i>, <i>street</i>, <i>city</i> or <i>email</i> of the customer record to be updated have to be specified.<br>
If, for example, <i>age</i>, <i>street</i>, <i>city</i> and <i>email address</i> are to be updated, then the following JSON object should be offered:

<code>
&nbsp;{<br>
&nbsp;&nbsp;  "age": "<i>age</i>",<br>
&nbsp;&nbsp;  "street": "<i>street</i>",<br>
&nbsp;&nbsp;  "city": "<i>city</i>",<br>
&nbsp;&nbsp;  "email": "<i>email address</i>"<br>
&nbsp;}
</code>

Be aware of the following with respect to the fields specified:
* The value for the <code>age</code> field has to be a number and the <code>email</code> field has to be in a valid email address format.<br>
They will be ignored if they aren't in which case the existing values will not be updated.
* The values of the other fields specified are not validated.
* The values for all fields have to be String formatted.

The values of the fields not specified in the JSON object will not be updated.

If the JSON object offered in the PUT is accepted, then a JSON object containing all fields with the values as they were _before_ the update will be returned.<br>
The response will be in the format as shown below.<br>

<code>
&nbsp;{<br>
&nbsp;&nbsp;  "id": "<i>id</i>",<br>
&nbsp;&nbsp;  "firstName": "<i>first name</i>",<br>
&nbsp;&nbsp;  "lastName": "<i>last name</i>",<br>
&nbsp;&nbsp;  "age": "<i>61</i>",<br>
&nbsp;&nbsp;  "street": "<i>street</i>",<br>
&nbsp;&nbsp;  "city": "<i>city</i>",<br>
&nbsp;&nbsp;  "email": "<i>email address</i>",<br>
&nbsp;}
</code>

##### Parameters

> | name |  type     | data type      | description                        |
> |------|-----------|----------------|------------------------------------|
> | `id` |  required | int            | The id of the record to be updated |
> | None |  required | object (JSON)  | See above                          |

##### Responses

> | http code | content-type             | response                           |
> |-----------|--------------------------|------------------------------------|
> | `200`     | `application/json`       | Customer object updated (JSON)     |
> | `400`     | `application/json`       | `Bad request`                      |
> | `500`     | `application/json`       | `No record found for ID specified` |


##### Example cURL

> ```curl
>  curl -X PUT -H "Content-Type: application/json" --data @customer.json  http://localhost:8080/update/1
> ```

</details>


#### Delete a Customer

<details>
  <summary><code>DELETE</code> <code><b>/delete/{id}</b></code></summary>

###### Deletes an existing record specified by its record id

The response will be the object deleted in the JSON format as shown below if it exists<br>

<code>
&nbsp;{<br>
&nbsp;&nbsp;  "id": "<i>id</i>",<br>
&nbsp;&nbsp;  "firstName": "<i>first name</i>",<br>
&nbsp;&nbsp;  "lastName": "<i>last name</i>",<br>
&nbsp;&nbsp;  "age": "<i>61</i>",<br>
&nbsp;&nbsp;  "street": "<i>street</i>",<br>
&nbsp;&nbsp;  "city": "<i>city</i>",<br>
&nbsp;&nbsp;  "email": "<i>email address</i>",<br>
&nbsp;}
</code>

##### Parameters

> | name    |  type     | data type | description                          |
> |---------|-----------|-----------|--------------------------------------|
> | `id`    |  required | int       | The id of the record to be deleted   |

##### Responses

> | http code | content-type             | response                           |
> |-----------|--------------------------|------------------------------------|
> | `200`     | `application/json`       | Customer object deleted (JSON)     |
> | `500`     | `application/json`       | `No record found for ID specified` |


##### Example cURL

> ```curl
>  curl -X DELETE http://localhost:8080/delete/1
> ```

</details>

#### Search a Customer

<details>
  <summary><code>GET</code> <code><b>/search?street={street}&city={city}</b></code></summary>

###### Find customer records on street and city

The response will be a JSON array with JSON objects in the format as shown below for each Customer record matching the search criteria.<br>

<code>
&nbsp;{<br>
&nbsp;&nbsp;  "id": "<i>id</i>",<br>
&nbsp;&nbsp;  "firstName": "<i>first name</i>",<br>
&nbsp;&nbsp;  "lastName": "<i>last name</i>",<br>
&nbsp;&nbsp;  "age": "<i>61</i>",<br>
&nbsp;&nbsp;  "street": "<i><b>street</b></i>",<br>
&nbsp;&nbsp;  "city": "<i><b>city</b></i>",<br>
&nbsp;&nbsp;  "email": "<i>email address</i>",<br>
&nbsp;}
</code>

The JSON array will be empty if no records can be found matching the search criteria.

##### Parameters

> | name     |  type     | data type | description                                               |
> |----------|-----------|-----------|-----------------------------------------------------------|
> | `street` |  required | String    | The value of the <code>street</code> field to search for  |
> | `city`   |  required | String    | The value for the <code>city</code> field to search for   |

##### Responses

> | http code | content-type             | response                         |
> |-----------|--------------------------|----------------------------------|
> | `200`     | `application/json`       | JSON array of JSON objects found |


##### Example cURL

> ```curl
> curl -X GET http://localhost:8080/search?street=Abbey%20Road%201&city=Liverpool
> ```

</details>

<details>
  <summary><code>GET</code> <code><b>/search?email={email}</b></code></summary>

###### Find customer records on email address

The response will be a JSON array with JSON objects in the format as shown below for each Customer record matching the search criteria.<br>

<code>
&nbsp;{<br>
&nbsp;&nbsp;  "id": "<i>id</i>",<br>
&nbsp;&nbsp;  "firstName": "<i>first name</i>",<br>
&nbsp;&nbsp;  "lastName": "<i>last name</i>",<br>
&nbsp;&nbsp;  "age": "<i>61</i>",<br>
&nbsp;&nbsp;  "street": "<i>street</i>",<br>
&nbsp;&nbsp;  "city": "<i>city</i>",<br>
&nbsp;&nbsp;  "email": "<i><b>email address</b></i>",<br>
&nbsp;}
</code>

The JSON array will be empty if no records can be found matching the search criteria.

##### Parameters

> | name    |  type     | data type | description                                             |
> |---------|-----------|-----------|---------------------------------------------------------|
> | `email` |  required | String    | The value of the <code>email</code> field to search for |

##### Responses

> | http code | content-type             | response                         |
> |-----------|--------------------------|----------------------------------|
> | `200`     | `application/json`       | JSON array of JSON objects found |


##### Example cURL

> ```curl
>  curl -X GET http://localhost:8080/saerch?email=paul.mccartney@gmail.com
> ```

</details>

<details>
  <summary><code>GET</code> <code><b>/search?firstName={first name}&lastName={last name}</b></code></summary>

###### Find customer records on first name and last name

The response will be a JSON array with JSON objects in the format as shown below for each Customer record matching the search criteria.<br>

<code>
&nbsp;{<br>
&nbsp;&nbsp;  "id": "<i>id</i>",<br>
&nbsp;&nbsp;  "firstName": "<i><b>first name</b></i>",<br>
&nbsp;&nbsp;  "lastName": "<i><b>last name</b></i>",<br>
&nbsp;&nbsp;  "age": "<i>61</i>",<br>
&nbsp;&nbsp;  "street": "<i>street</i>",<br>
&nbsp;&nbsp;  "city": "<i>city</i>",<br>
&nbsp;&nbsp;  "email": "<i>email address</i>",<br>
&nbsp;}
</code>

The JSON array will be empty if no records can be found matching the search criteria.

##### Parameters

> | name        |  type     | data type | description                                                 |
> |-------------|-----------|-----------|-------------------------------------------------------------|
> | `firstName` |  required | String    | The value of the <code>firstName</code> field to search for |
> | `lastName`  |  required | String    | The value for the <code>lastName</code> field to search for |

##### Responses

> | http code | content-type             | response                         |
> |-----------|--------------------------|----------------------------------|
> | `200`     | `application/json`       | JSON array of JSON objects found |


##### Example cURL

> ```curl
> curl -X GET http://localhost:8080/search?firstName=Paul&lastName=McCartney
> ```

</details>

#### List a Customer 

<details>
  <summary><code>GET</code> <code><b>/list/{id}</b></code></summary>

###### Lists an existing customer record specified by its record id

The response will be the object specified in the JSON format as shown below if it exists<br>

<code>
&nbsp;{<br>
&nbsp;&nbsp;  "id": "<i><b>id</b></i>",<br>
&nbsp;&nbsp;  "firstName": "<i>first name</i>",<br>
&nbsp;&nbsp;  "lastName": "<i>last name</i>",<br>
&nbsp;&nbsp;  "age": "<i>61</i>",<br>
&nbsp;&nbsp;  "street": "<i>street</i>",<br>
&nbsp;&nbsp;  "city": "<i>city</i>",<br>
&nbsp;&nbsp;  "email": "<i>email address</i>",<br>
&nbsp;}
</code>

##### Parameters

> | name    |  type     | data type | description                          |
> |---------|-----------|-----------|--------------------------------------|
> | `id`    |  required | int       | The id of the record to be retrieved |

##### Responses

> | http code | content-type             | response                           |
> |-----------|--------------------------|------------------------------------|
> | `200`     | `application/json`       | JSON object retrieved (JSON)       |
> | `500`     | `application/json`       | `No record found for ID specified` |


##### Example cURL

> ```curl
>  curl -X GET http://localhost:8080/list/1
> ```

</details>

#### List all Customers

<details>
  <summary><code>GET</code> <code><b>/list</b></code></summary>

###### Lists all records in the Customer repository

The response will be a JSON array with JSON objects in the format as shown below for each Customer record in the repository.<br>

<code>
&nbsp;{<br>
&nbsp;&nbsp;  "id": "<i>id</i>",<br>
&nbsp;&nbsp;  "firstName": "<i><b>first name</b></i>",<br>
&nbsp;&nbsp;  "lastName": "<i><b>last name</b></i>",<br>
&nbsp;&nbsp;  "age": "<i>61</i>",<br>
&nbsp;&nbsp;  "street": "<i>street</i>",<br>
&nbsp;&nbsp;  "city": "<i>city</i>",<br>
&nbsp;&nbsp;  "email": "<i>email address</i>",<br>
&nbsp;}
</code>

The JSON array will be empty if the repository has no records.

##### Parameters

No parameters have to be provided.

##### Responses

> | http code | content-type             | response                           |
> |-----------|--------------------------|------------------------------------|
> | `200`     | `application/json`       | JSON array of JSON objects found   |


##### Example cURL

> ```curl
>  curl -X GET http://localhost:8080/list
> ```

</details>

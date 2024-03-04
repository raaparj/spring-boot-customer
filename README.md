## Customer application REST API specification

This is a sample Customer application to store, search, update and delete customer data.<br>
It has the following end-points available: 

#### Add a Customer

<details>
 <summary><code>POST</code> <code><b>/add</b></code> <code>(adds a Customer object to the repository)</code></summary>

The JSON object posted must specify the <i>first name</i>, <i>last name</i>, <i>age</i>, <i>street</i>, <i>city</i> and <i>email address</i> of the customer to be registered:

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

The value for the <code>age</code> field has to be a number in digits and the <code>email</code> field has to be in a valid email address format.<br>
They will be ignored if they aren't.<br>
The values of the other fields are not validated.
The values for all fields have to be String formatted.

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

Be aware that the same record (as an identical JSON object) can be added and more than once and, if so, will be stored in the repository multiple times.<br>
Records will differ on the record id's, though.

##### Parameters

> | name      |  type     | data type               | description                                                           |
> |-----------|-----------|-------------------------|-----------------------------------------------------------------------|
> | None      |  required | object (JSON)   | N/A  |


##### Responses

> | http code | content-type             | response                                    |
> |-----------|--------------------------|---------------------------------------------|
> | `200`     | `application/json`       | Customer object added with record id (JSON) |
> | `400`     | `application/json`       | `Bad request`                               |
> | `415`     | `application/json`       | `Unsupported Media Type`                    |


##### Example cURL

> ```curl
>  curl -X POST -H "Content-Type: application/json" --data @customer.json http://localhost:8080/add
> ```


</details>

#### Update a Customer

<details>
  <summary><code>PUT</code> <code><b>/update/{id}?street={street}&city={city}</b></code> <code>(updates street and city in an existing record specified by its record id )</code></summary>


The response will be a JSON object in the format as shown below as it will be after the update<br>

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

##### Parameters

> | name     |  type     | data type | description                                     |
> |----------|-----------|-----------|-------------------------------------------------|
> | `id`     |  required | int       | The id of the record to be updated              |
> | `street` |  required | String    | The new value for the <code>street</code> field |
> | `city`   |  required | String    | The new value for the <code>city</code> field   |

##### Responses

> | http code | content-type             | response                           |
> |-----------|--------------------------|------------------------------------|
> | `200`     | `application/json`       | Customer object updated (JSON)     |
> | `500`     | `application/json`       | `No record found for ID specified` |


##### Example cURL

> ```curl
>  curl -X PUT http://localhost:8080/update/1?street=Abbey%20Road%201&city=Liverpool
> ```

</details>

<details>
  <summary><code>PUT</code> <code><b>/update/{id}?email={email}</b></code> <code>(updates email in an existing record specified by its record id )</code></summary>

The response will be a JSON object in the format as shown below as it will be after the update<br>

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

##### Parameters

> | name    |  type     | data type | description                                    |
> |---------|-----------|-----------|------------------------------------------------|
> | `id`    |  required | int       | The id of the record to be updated             |
> | `email` |  required | String    | The new value for the <code>email</code> field |

##### Responses

> | http code | content-type             | response                           |
> |-----------|--------------------------|------------------------------------|
> | `200`     | `application/json`       | Customer object updated (JSON)     |
> | `500`     | `application/json`       | `No record found for ID specified` |


##### Example cURL

> ```curl
>  curl -X PUT http://localhost:8080/update/1?email=paul.mccartney@gmail.com
> ```

</details>

#### Delete a Customer

<details>
  <summary><code>DELETE</code> <code><b>/delete/{id}</b></code> <code>(deletes an existing record specified by its record id )</code></summary>

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
  <summary><code>GET</code> <code><b>/search?street={street}&city={city}</b></code> <code>(find customer records on street and city )</code></summary>


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
  <summary><code>GET</code> <code><b>/search?email={email}</b></code> <code>(find customer records on email address )</code></summary>


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
  <summary><code>GET</code> <code><b>/search?firstName={first name}&lastName={last name}</b></code> <code>(find customer records on first name and last name )</code></summary>


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

#### List (a) Customer(s) 

<details>
  <summary><code>GET</code> <code><b>/list/{id}</b></code> <code>(lists an existing record specified by its record id )</code></summary>

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

<details>
  <summary><code>GET</code> <code><b>/list</b></code> <code>(lists all records in the Customer repository )</code></summary>

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

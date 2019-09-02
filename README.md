# lottery

A simple lottery REST interface.

|HttpMethod|Endpoint|Description|
| ------------- | ------------- | ------------- |
|POST|/ticket|Creates a ticket and returns it in the response body|
|GET|/ticket|Returns a list of all tickets|
|GET|/ticket/{id}|Returns a ticket with the id provided, if it exists|
|PUT|/ticket/{id}|Add lines to a ticket with the id provided, if it exists, and if the status has not been checked|
|PUT|/status/{id}|Retrieve the status of a ticket with the id provided, if it exists|

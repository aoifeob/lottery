# lottery

A simple lottery REST interface.

|HttpMethod|Endpoint|Body|Description|
| ------------- | ------------- | ------------- | ------------- |
|POST|/ticket|int value to determine the number of lines on the ticket (eg "4")|Creates a ticket and returns it in the response body|
|GET|/ticket|None|Returns a list of all tickets|
|GET|/ticket/{id}|None|Returns a ticket with the id provided, if it exists|
|PUT|/ticket/{id}|int value to determine the number of lines to add to the ticket (eg "2")|Add lines to a ticket with the id provided, if it exists, and if the status has not been checked|
|PUT|/status/{id}|None|Retrieve the status of a ticket with the id provided, if it exists|

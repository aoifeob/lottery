# lottery

A simple lottery REST interface.

|HttpMethod|Endpoint|Body|Description|
| ------------- | ------------- | ------------- | ------------- |
|POST|/ticket|int value to determine the number of lines on the ticket (eg "4")|Creates a ticket and returns it in the response body.|
|GET|/ticket|None|Returns a list of all tickets.|
|GET|/ticket/{id}|None|Returns a ticket with the id provided, if it exists. If the ticket does not exist, returns a 404.|
|PUT|/ticket/{id}|int value to determine the number of lines to add to the ticket (eg "2")|Add lines to a ticket with the id provided, if it exists, and if the status has not been checked. If the ticket does not exist, returns a 404. If the ticket status has already been checked, returns a 422.|
|PUT|/status/{id}|None|Retrieve the status of a ticket with the id provided, if it exists. Ticket lines are returned in order of ascending result. If the ticket does not exist, returns a 404.|

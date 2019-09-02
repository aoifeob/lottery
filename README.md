# lottery

A simple lottery REST interface.

## Rules
* You have a series of lines on a ticket with 3 numbers, each of which has a value of 0, 1, or 2.
* For each ticket, if the sum of the values on a line is 2, the result for that line is 10.
* Otherwise, if they are all the same, the result is 5.
* Otherwise, so long as both 2nd and 3rd numbers are different from the 1st, the result is 1.
* Otherwise, the result is 0.

## API Documentation

|HttpMethod|Endpoint|Body|Description|
| ------------- | ------------- | ------------- | ------------- |
|POST|/ticket|int value to determine the number of lines on the ticket (eg "4")|Creates a ticket and returns it in the response body.|
|GET|/ticket|None|Returns a list of all tickets.|
|GET|/ticket/{id}|None|Returns a ticket with the id provided, if it exists. If the ticket does not exist, returns a 404.|
|PUT|/ticket/{id}|int value to determine the number of lines to add to the ticket (eg "2")|Add lines to a ticket with the id provided, if it exists, and if the status has not been checked. If the ticket does not exist, returns a 404. If the ticket status has already been checked, returns a 422.|
|PUT|/status/{id}|None|Retrieve the status of a ticket with the id provided, if it exists. Ticket lines are returned in order of ascending result. If the ticket does not exist, returns a 404.|

## Test Coverage
<img src="https://raw.githubusercontent.com/aoifeob/lottery/master/codeCoverage.png">

## Running Instructions (IntelliJ)

Requirements: Java 8, Maven

* Clone the repository to your local machine and open the project in Intellij
* Run `mvn clean install`
* Select the `LotteryApplication` configuration and click debug. The service will start on `localhost:8080`
* Use Postman or CURL to hit the above endpoints
* Test classes can also be run using IntelliJ

# Understanding http basics

## HTTP --> HIGH LEVEL PROTOCOL

1. _Hypertext Transfer Protocol_ is a protocol responsible for how the data is requested/delivered
2. It is client-server protocol, i.e. requests is initiated by client(typically browser) and server responses it

## Protocol

: set of rules that defined how the data is exchange within computers. here it is between client and server computers

1. What is HTTP?

Definition: HTTP is a protocol used for communication between clients (e.g., web browsers, apps) and servers over the internet.

Stateless: Each HTTP request is independent, meaning the server does not retain information about past requests from the same client.

2. HTTP methods

: define the type of actions to perform (whether client is sending data to the server or server is sending to the client)

- GET : retrieve data from the server
- POST : send data to the server to update or create a resource
- PUT : update and create resource
- DELETE : delete data present in the server

3. HTTP request

: an HTTP request is sent by the client to requests resources (like a wep page, images, or API data)

Request Line:

    Example: GET /index.html HTTP/1.1  --> request by the browser when we write http:://google.com/index.html
        GET: The method (action to perform).
        /index.html: The path or resource being requested.
        HTTP/1.1: The protocol version.

Headers: Metadata about the request (e.g., Content-Type, Authorization). provided by the browser for GET

    Example:

    User-Agent: Mozilla/5.0
    Accept: text/html

Body: Data sent with the request (used with methods like POST or PUT).

    Example (for JSON data):

{
"username": "hirak",
"password": "securepassword"
}

4. Automatic Behavior by Browsers

   Browsers handle many details automatically:

   - Sending the GET request.
   - Managing the TCP connection.
   - Parsing the server’s response (HTML, CSS, JavaScript, etc.).
   - Rendering the page content visually for the user.

# TCP --> LOW LEVEL PROTOCOL

- Designed to send small packets(breaks data into small pieces) across the internet and ensure the successful delivery of data and messages over networks. client < - > server

- end to end data delivery --> to deliver data TCP connection is required.

- TCP protocol is used by all High level protocols that need to to transmit data.
  High level protocols are HTTP, FTP, SSH, Telnet

1. High-Level vs. Low-Level Protocols

   HTTP:
   A high-level protocol that defines how data (like web pages, images, etc.) is requested and delivered.
   Focuses on the content structure (e.g., requests, responses, headers, methods).
   Doesn't handle data transfer mechanics directly; instead, it uses underlying protocols like TCP.

   TCP:
   A transport layer protocol that ensures reliable delivery of data between two devices.
   Manages the actual transmission of data packets over the network, ensuring they arrive in order and without errors.

2. who makes the TCP connection?

The TCP connection is initiated by the client, typically your browser or application, as part of the process of making an HTTP request. Here's a breakdown of the roles:

1. TCP Connection Initiation

   Client (e.g., Browser, App):
   The client is responsible for initiating the TCP connection to the server.
   It resolves the server's hostname (e.g., example.com) into an IP address using DNS.
   Once the IP is known, the client sends a TCP connection request to the server's IP address on the appropriate port (e.g., port 80 for HTTP or port 443 for HTTPS).

   Server:
   The server listens for incoming TCP connection requests on specific ports (e.g., port 80 for HTTP or 443 for HTTPS).
   When it receives a request, it responds to establish the connection.

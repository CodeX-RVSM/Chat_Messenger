### Chat_Messenger

    It is a Java-based peer-to-peer chat application built using socket programming. The main objective of this project is to enable real-time communication between a server and a client while maintaining a chat log with timestamps.

## How to Compile:
    javac Server.java
    javac Client.java

## How to Run:
    Open Terminal 1:
    java Server

## Open Terminal 2:
    java Client

## Commands:
    Type messages and press Enter.
    Type exit to close connection.

## Log File:
    chatlog.txt
## How It Works
    1. Server opens a port and waits for client connection.
    2. Client connects using server IP and port.
    3. Usernames are exchanged.
    4. Separate threads handle sending and receiving.
    5. Messages are displayed with timestamps.
    6. All messages are stored in `chatlog.txt`.
    7. Typing `exit` closes the connection gracefully.

## Author
    **Rushikesh Mangalkar**  
    Computer Science & Engineering Student
    Technology: Java Socket Programming
    Year: 2026


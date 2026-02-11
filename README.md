# TCP Chat Application

A simple multi-client chat application built with **Java Sockets (TCP)**. It allows multiple users to connect to a central server and exchange messages in real-time.

---

## Features
- **Multi-User Support**: Handles multiple clients simultaneously using threads.
- **Real-Time Messaging**: Instant message broadcasting to all connected users.
- **User Notifications**: Alerts everyone when a user joins or leaves.
- **Graceful Shutdown**: Handles client disconnections without crashing the server.

---

## Tech Stack
- **Language**: Java
- **Networking**: `java.net.Socket`, `java.net.ServerSocket` (Blocking I/O)
- **Concurrency**: `Thread`, `ConcurrentHashMap`

---

## Project Structure
```
TCP_Chat/
├── app/
│   ├── Server.java       # The chat server (handles connections & broadcasting)
│   ├── EchoClient.java   # The chat client (sends/receives messages)
└── README.md             # This file
```

---

## How to Run

### prerequisites
- Java Development Kit (JDK) 8 or higher.

### 1. Compile the Code
Open your terminal in the root `TCP_Chat` folder:
```bash
javac app/*.java
```

### 2. Start the Server
Run the server first. It will listen on port `12345`.
```bash
java app.Server
```
*Expected Output:*
```
Server started on port 12345
```

### 3. Start a Client
Open a **new terminal window** (keep the server running!) and start a client:
```bash
java app.EchoClient
```
*Expected Output:*
```
Connected to server.
Enter your username: Alice
```
*   Type your messages and press Enter to send.
*   Type `exit` to leave the chat.

### 4. add More Users
Open more terminal windows and run `java app.EchoClient` again to simulate multiple users (Bob, Charlie, etc.).

---

## Code Overview

### Server (`Server.java`)
- Listens for incoming connections on port `12345`.
- Spawns a **new thread** for every client to handle their messages independently.
- Maintains a thread-safe list of active users (`clientWriters`).
- Broadcasts incoming messages to all other connected clients.

### Client (`EchoClient.java`)
- Connects to `localhost` on port `12345`.
- Uses **two threads**:
    1.  **Main Thread**: Reads user input from the console and sends it to the server.
    2.  **Reader Thread**: continuously listens for incoming messages from the server and prints them.

---

## Troubleshooting
- **`java.net.BindException: Address already in use`**: The server is already running. Close the previous terminal or kill the process.
- **`java.net.ConnectException: Connection refused`**: Ensure the server is running *before* starting the client.
- **`Error: Could not find or load main class app.Server`**: Make sure you run the command from the `TCP_Chat` root folder, NOT inside `app/`.

package app;

import java.io.*;
import java.net.*;

import java.util.*;
import java.util.concurrent.*;

public class Server {
    // Thread-safe set to store all connected client writers
    private static Set<PrintWriter> clientWriters = ConcurrentHashMap.newKeySet();

    public static void main(String[] args) throws Exception {
        int port = 12345;
        ServerSocket server = new ServerSocket(port);
        System.out.println("Server started on port " + port);

        try {
            while (true) {
                Socket client = server.accept();
                System.out.println("Client connected: " + client.getRemoteSocketAddress());

                // Start a new thread for each client
                new Thread(() -> handleClient(client)).start();
            }
        } finally {
            server.close();
        }
    }

    public static void handleClient(Socket client) {
        String username = null;
        PrintWriter out = null;
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            out = new PrintWriter(client.getOutputStream(), true);

            // First line from client is the username
            username = in.readLine();
            if (username == null) {
                return;
            }

            synchronized (clientWriters) {
                clientWriters.add(out);
            }

            System.out.println("User '" + username + "' joined.");
            broadcast("Server: " + username + " has joined the chat.");

            String line;
            while ((line = in.readLine()) != null) {
                System.out.println("Received from " + username + ": " + line);
                broadcast(username + ": " + line);
            }
        } catch (IOException e) {
            System.out.println("Error handling client " + username + ": " + e.getMessage());
        } finally {
            if (out != null) {
                clientWriters.remove(out);
            }
            if (username != null) {
                System.out.println("User '" + username + "' left.");
                broadcast("Server: " + username + " has left the chat.");
            }
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Send message to all connected clients
    public static void broadcast(String message) {
        for (PrintWriter writer : clientWriters) {
            writer.println(message);
        }
    }
}

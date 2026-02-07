package app;
import java.io.*;
import java.net.*;

class Server1 {
    public static void main(String[] args) throws Exception {
        int port = 12345;
        ServerSocket server = new ServerSocket(port);
        System.out.println("Server started on port " + port);

        while (true) {
            Socket client = server.accept();
            System.out.println("Client connected: " + client.getRemoteSocketAddress());

            Thread t = new Thread(() -> handleClient(client));
            t.start();
        }
    }

    public static void handleClient(Socket client) {
        try (
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            PrintWriter out = new PrintWriter(client.getOutputStream(), true)
        ) {
            String line;
            while ((line = in.readLine()) != null) {
                System.out.println("Received from client: " + line);
                out.println("Echo back: " + line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection(client);
        }
    }

    public static void closeConnection(Socket client) {
        try {
            client.close();
            System.out.println("Client connection closed.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


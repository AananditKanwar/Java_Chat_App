package app;

import java.io.*;
import java.net.*;
import java.util.*;

public class EchoClient {
  public static void main(String[] args) {
    String hostServer = "localhost";
    int serverPort = 12345;
    try {
      Socket socket = new Socket(hostServer, serverPort);
      BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
      Scanner scn = new Scanner(System.in);

      System.out.println("Connected to server.");
      System.out.print("Enter your username: ");
      String username = scn.nextLine();
      out.println(username);

      // Thread for reading from server
      Thread readThread = new Thread(() -> {
        try {
          String msg;
          while ((msg = in.readLine()) != null) {
            System.out.println(msg);
          }
        } catch (IOException e) {
          System.out.println("Connection closed.");
        }
      });
      readThread.start();

      // Main thread for writing to server
      while (true) {
        String msg = scn.nextLine();
        if ("exit".equalsIgnoreCase(msg)) {
          break;
        }
        out.println(msg);
      }

      socket.close();
      scn.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}

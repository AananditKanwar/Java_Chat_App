package app;
import java.io.*;
import java.net.*;
import java.util.*;

public class EchoClient {
  public static void main(String args[]) {
    String hostServer = "localhost";
    int serverPort = 12345;
    try (Socket socket = new Socket(hostServer, serverPort);
      BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
      Scanner scn = new Scanner(System.in);
    ) {
        System.out.println("Client Connection is made with the server");
	String msg;
	while (true) {
	  System.out.print("Enter the Line : ");
	  msg = scn.nextLine();
	  if ("exit".equals(msg)) break;
          out.println(msg);
          System.out.println("Server Replied back : " + in.readLine());
	}  
      } catch(Exception e) {
	  e.printStackTrace();
    }
  }
}


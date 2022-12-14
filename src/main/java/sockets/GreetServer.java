package sockets;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class GreetServer {

    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public void start(int port) throws IOException {
        // new 一个 ServerSocket s
        // s.accept 等待消息

        serverSocket = new ServerSocket(port);
        System.out.println("server waiting...");
        clientSocket = serverSocket.accept();
        System.out.println("server accept success!");

        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        String greeting = in.readLine();
        if ("hello server".equals(greeting)) {
            out.println("hello client");
        }
        else {
            out.println("unrecognised greeting");
        }
    }

    public void stop() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
        serverSocket.close();
    }
    public static void main(String[] args) throws IOException {
        GreetServer server=new GreetServer();
        server.start(6667);
    }

}

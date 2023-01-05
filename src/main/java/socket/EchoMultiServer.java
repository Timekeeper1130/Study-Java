package socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoMultiServer {
    private ServerSocket serverSocket;

    public static void main(String[] args) throws IOException {
        EchoMultiServer server = new EchoMultiServer();
        server.start(4444);
    }

    public void start(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        int clientCount = 0;
        while (true){
            // accept等待客户端的连接
            System.out.println("--等待第" + ++clientCount + "个客户端连接--");
            new EchoClientHandler(serverSocket.accept()).start();
        }
    }

    public void stop() throws IOException {
        serverSocket.close();
    }

    private static class EchoClientHandler extends Thread {
        private Socket clientSocket;
        private PrintWriter out;
        private BufferedReader in;

        public EchoClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        public void run() {
            try {
                out = new PrintWriter(clientSocket.getOutputStream(), true);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try {
                in = new BufferedReader(
                        new InputStreamReader(clientSocket.getInputStream()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            String inputLine;
            while (true) {
                try {
                    if (!((inputLine = in.readLine()) != null)) {
                        break;
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                if (".".equals(inputLine)) {
                    out.println("bye");
                    break;
                }
                out.println(inputLine);
            }

            try {
                in.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            out.close();
            try {
                clientSocket.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
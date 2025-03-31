import java.net.*;
import java.io.*;

class Server {

    ServerSocket server;
    Socket socket;

    BufferedReader br;
    PrintWriter out;

    public Server() {
        try {
            server = new ServerSocket(8080);
            System.out.println("Server is ready to accept connections.");
            System.out.println("Waiting...");
            socket = server.accept();

            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());

            startReading();
            startWriting();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startReading() {
        // thread - read and keep delivering messages
        Runnable r1 = () -> {
            System.out.println("Reader started...");

            while (true) {
                try {
                    String msg = br.readLine();
                    if (msg.equals("exit")) {
                        System.out.println("Client terminated the chat.");

                        socket.close(); //ye likhne se connection close ho jayega agar data bhejhenge toh exception aayegi;
                        break;
                    }
                    System.out.println("Client: " + msg);
                } catch (Exception e) {
                    e.printStackTrace(); // This prints the stack trace of the error.
                }
            }
  
        };
        new Thread(r1).start();
    }

    public void startWriting() {
        // thread - data from user input and then sends to client
        Runnable r2 = () -> {
            System.out.println("Writer started...");
            while (true) {
                try {
                    BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
                    String content = br1.readLine();
                    out.println(content);
                    out.flush();

                    if (content.equals("exit")) {
                        System.out.println("You terminated the chat.");
                        break;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        new Thread(r2).start();
    }

    public static void main(String[] args) {
        System.out.println("This is the server starting...");
        new Server();
    }
}

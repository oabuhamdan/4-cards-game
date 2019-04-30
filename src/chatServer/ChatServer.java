package chatServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ChatServer {
    public static void main(String[] args) {
        List<String> messages = new ArrayList<>();
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(8001);
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (true) {
            try {
                Socket client = serverSocket.accept();
                MessagesThread messagesThread = new MessagesThread(client, messages);
                messagesThread.start();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
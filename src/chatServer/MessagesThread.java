package chatServer;

import java.io.*;
import java.net.Socket;
import java.util.List;

public class MessagesThread extends Thread {
    String message;
    private List<String> messages;
    private Socket client;

    public MessagesThread(Socket client, List<String> messages) {
        this.client = client;
        this.messages = messages;
    }

    @Override
    public void run() {
        while (true) {
            try {
                InputStream is = client.getInputStream();
                DataInputStream dis = new DataInputStream(is);
                message = dis.readUTF();
                messages.add(message);
                OutputStream os = client.getOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(os);
                oos.writeObject(messages);
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
    }
}

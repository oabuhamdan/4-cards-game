package chatServer;

import java.io.DataInputStream;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
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
        InputStream inputStream;
        DataInputStream dataInputStream;
        while (true) {
            try {
                String operation;
                inputStream = client.getInputStream();
                dataInputStream = new DataInputStream(inputStream);
                operation = dataInputStream.readUTF();
                switch (operation) {
                    case "sendMessage":
                        sendMessage();
                        break;
                    case "getMessages":
                        getMessages();
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void getMessages() {
        try {
            OutputStream os = client.getOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(os);
            oos.writeObject(messages);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendMessage() {
        try {
            InputStream is = client.getInputStream();
            DataInputStream dis = new DataInputStream(is);
            message = dis.readUTF();
            messages.add(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


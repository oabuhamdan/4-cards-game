package Objects;

import java.net.Socket;

public class ClientSocket {
    private static Socket socket;

    private ClientSocket() {
    }

    //Get the only object available
    public static Socket getInstance() {
        return socket;
    }

    public static void createNewConnection() {
        try {
            socket = new Socket("127.0.0.1", 8888);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void terminateConnection() {
        try {
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}

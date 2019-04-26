import Objects.Challenge;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class MyThread extends Thread{
    Socket s ;
    InetAddress client;
    private  List<Challenge> challenges=new ArrayList<>();

    public MyThread(Socket s, List<Challenge> challenges) {
        this.s = s;
        this.challenges = challenges;
    }

    @Override
    public void run() {
        client = s.getInetAddress();
        try {
            //sending challenges to the client
            OutputStream outputStream = s.getOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(challenges);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

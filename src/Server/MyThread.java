package Server;

import Objects.Challenge;

import java.io.*;
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
            String operation;
            InputStream inputStream =s.getInputStream();
            DataInputStream dataInputStream = new DataInputStream(inputStream);
            operation = dataInputStream.readLine();
            switch (operation){
                case"getChallenges":
                    sendChallenges();
                    //TODO getChallenges(); send Server.challenges each challenge should include the 4 photos explicitly (not an object)
                    break;
            }
            //sending Server.challenges to the client
            OutputStream outputStream = s.getOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(challenges);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendChallenges() throws IOException {
        int numOfChallenges = getNumOfChallenges();
        FileInputStream fis;
        OutputStream outputStream = s.getOutputStream();
        File f ;
        byte[] buffer;
        for(Integer i = 1 ; i<=numOfChallenges;i++){//i is the folder with a challenge
            for(Integer j = 1 ; j < 5;i++){         //j is the photo name which will be numbers from 1 to 4
                f = new File("challenges\\"+i.toString()+"\\"+j.toString());
                fis = new FileInputStream(f);
                buffer = new byte[fis.available()];
                fis.read(buffer);
                outputStream.write(buffer);
            }
        }
    }

    private int getNumOfChallenges(){
        File file = new File("Server/challenges");
        String[] directories = file.list(new FilenameFilter() {
            @Override
            public boolean accept(File current, String name) {
                return new File(current, name).isDirectory();
            }
        });
        return directories.length;
    }
}

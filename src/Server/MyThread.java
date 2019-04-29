package Server;

import Objects.User;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class MyThread extends Thread {
    Socket s;
    InetAddress client;


    public MyThread(Socket s) {
        this.s = s;
    }

    @Override
    public void run() {
        client = s.getInetAddress();
        InputStream inputStream;
        DataInputStream dataInputStream;

        try {
            String operation;
            inputStream = s.getInputStream();
            dataInputStream = new DataInputStream(inputStream);
            operation = dataInputStream.readUTF();
            switch (operation) {
                case "getChallenges":
                    //sendChallenges();
                    //TODO getChallenges(); send LocalDataHandler.challenges each challenge should include the 4 photos explicitly (not an object)
                    break;
                case "uploadImages":
                    receiveImages();
                    System.out.println("uploadImages");
                    break;
                case "getImagesFolder":
                    sendFilesToClient();
                    System.out.println("getImagesFolder");
                    break;
                case "createUser":
                    createUser();
                    System.out.println("createUser");
                    break;
                case "checkCardinality":
                    checkCardinality();
                    System.out.println("checkCardinality");
                    break;
                case "addScoreToUser":
                    addScoreToUser();
                    break;
                case "getLeaderBoard":
                    getLeaderBoard();
                    break;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getLeaderBoard() {
        try {
            List<User> users = MainServer.users;
            users.sort(Comparator.comparingInt(User::getScore));
            //users.forEach(System.out::println);
            ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
            oos.writeObject(oos);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void addScoreToUser() {
        try {
            DataInputStream dis = new DataInputStream(s.getInputStream());
            String userName = dis.readUTF();
            int score = dis.readInt();
            for (User user : MainServer.users) {
                if (userName.equalsIgnoreCase(user.getUserName()))
                    user.increaseScore(score);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void checkCardinality() {
        try {
            DataInputStream dis = new DataInputStream(s.getInputStream());
            DataOutputStream dos = new DataOutputStream(s.getOutputStream());
            String userName = dis.readUTF();
            String password = dis.readUTF();
            for (User user : MainServer.users) {
                if (userName.equalsIgnoreCase(user.getUserName()) && password.equals(user.getPassword()))
                    dos.writeUTF("true");
            }
            dos.writeUTF("false");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createUser() {
        try {
            DataInputStream dis = new DataInputStream(s.getInputStream());
            String userName = dis.readUTF();
            String password = dis.readUTF();
            String email = dis.readUTF();
            MainServer.users.add(new User(userName, password, 0, email));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendChallenges() throws IOException {
        int numOfChallenges = getNumOfChallenges();
        FileInputStream fis;
        OutputStream outputStream = s.getOutputStream();
        File f;
        byte[] buffer;
        for (int i = 1; i <= numOfChallenges; i++) {//i is the folder with a challenge
            for (int j = 1; j < 5; i++) {         //j is the photo name which will be numbers from 1 to 4
                f = new File("challenges\\" + Integer.toString(i) + "\\" + Integer.toString(j));
                fis = new FileInputStream(f);
                buffer = new byte[fis.available()];
                fis.read(buffer);
                outputStream.write(buffer);
            }
        }
    }

    private int getNumOfChallenges() {
        File dir = new File("ServerSideChallenges");
        int numberOfSubfolder = 0;
        File listDir[] = dir.listFiles();
        for (File file : listDir) {
            if (file.isDirectory()) {
                numberOfSubfolder++;
            }
        }
        return numberOfSubfolder - 1;
    }

    private void receiveImages() {
        int challengeNumber = getNumOfChallenges() + 1;
        new File("ServerSideChallenges/" + challengeNumber).mkdir();
        try {
            BufferedInputStream bis = new BufferedInputStream(s.getInputStream());
            DataInputStream dis = new DataInputStream(bis);
            for (int i = 0; i < 4; i++) {
                long fileLength = dis.readLong();
                FileOutputStream fos = new FileOutputStream(new File("ServerSideChallenges/" + challengeNumber + "/image" + i + ".png"));
                BufferedOutputStream bos = new BufferedOutputStream(fos);
                for (int j = 0; j < fileLength; j++) bos.write(bis.read());
                bos.close();
            }
            dis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendFilesToClient() {
        try {
            int selectedFolder;
            InputStream inputStream = s.getInputStream();
            DataInputStream dataInputStream = new DataInputStream(inputStream);
            selectedFolder = dataInputStream.readInt();

            File dir = new File("ServerSideChallenges" + File.separator + selectedFolder);

            List<File> images = Arrays.asList(dir.listFiles());

            DataOutputStream dos = new DataOutputStream(s.getOutputStream());

            for (File file : images) {
                long length = file.length();
                dos.writeLong(length);
                FileInputStream fis = new FileInputStream(file);
                BufferedInputStream bis = new BufferedInputStream(fis);
                int theByte;
                while ((theByte = bis.read()) != -1) dos.write(theByte);
                bis.close();
            }
            dos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
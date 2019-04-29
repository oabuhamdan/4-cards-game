package Server;

import Objects.Challenge;
import Objects.User;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
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
                    sendChallenges();
                    System.out.println("sendChallenges");
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
                case "createChallenge":
                    createChallenge();
                    System.out.println("createChallenge");
                    break;
                case "checkCardinality":
                    checkCardinality();
                    System.out.println("checkCardinality");
                    break;
                case "addScoreToUser":
                    addScoreToUser();
                    System.out.println("addScoreToUser");
                    break;
                case "getLeaderBoard":
                    getLeaderBoard();
                    System.out.println("getLeaderBoard");
                    break;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getLeaderBoard() {
        try {
            List<User> users = MainServer.users;
            users.add(new User("osama", "", 20, ""));
            users.add(new User("osama", "", 50, ""));
            users.sort(Comparator.comparingInt(User::getScore));
            ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
            oos.writeObject(users);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addScoreToUser() {
        try {
            DataInputStream dis = new DataInputStream(s.getInputStream());
            String userName = dis.readUTF();
            int score = dis.readInt();
            for ( User user : MainServer.users ) {
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
            for ( User user : MainServer.users ) {
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
            User user = new User(userName, password, 0, email);
            MainServer.users.add(user);
            serialize(MainServer.users, "Users.txt");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createChallenge() {
        try {
            DataInputStream dis = new DataInputStream(s.getInputStream());
            String word = dis.readUTF();
            String time = dis.readUTF();
            String creator = dis.readUTF();
            Challenge challenge = new Challenge(time, word, creator);
            MainServer.challenges.add(challenge);
            serialize(MainServer.challenges, "Challenges.txt");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void serialize(Object object, String file) {
        OutputStream outputStream;
        try {
            outputStream = new FileOutputStream(file);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(object);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendChallenges() throws IOException {
        try {
            List<Challenge> challenges = MainServer.challenges;
            ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
            oos.writeObject(challenges);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private int getNumOfChallenges() {
        File dir = new File("ServerSideChallenges");
        int numberOfSubfolder = 0;
        File[] listDir = dir.listFiles();
        for ( File file : listDir ) {
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
            for ( int i = 0; i < 4; i++ ) {
                long fileLength = dis.readLong();
                FileOutputStream fos = new FileOutputStream(new File("ServerSideChallenges/" + challengeNumber + "/image" + i + ".png"));
                BufferedOutputStream bos = new BufferedOutputStream(fos);
                for ( int j = 0; j < fileLength; j++ ) bos.write(bis.read());
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

            File[] images = dir.listFiles();

            DataOutputStream dos = new DataOutputStream(s.getOutputStream());

            for ( File file : images ) {
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
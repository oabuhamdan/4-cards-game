package Objects;


import java.io.File;
import java.io.Serializable;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

public class Challenge implements Serializable {
    private String time;
    private String relatedWord;
    private List<File> images;
    private int folderIndex;

    public Challenge() {
    }

    public Challenge(String time, String relatedWord, List<File> images) {
        this.time = time;
        this.relatedWord = relatedWord;
        this.images = images;
    }

    public String getTime() {
        return time;
    }

    public String getRelatedWord() {
        return relatedWord;
    }

    public List<File> getImages() {
        File dir = new File("ClientSideChallenges" + File.separator + folderIndex);
        List<File> images = Arrays.asList(dir.listFiles());
        return images;
    }

    public void setFolderIndex(int index) {
        this.folderIndex = index;
    }
}

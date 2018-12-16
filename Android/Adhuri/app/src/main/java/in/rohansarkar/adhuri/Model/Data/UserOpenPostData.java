package in.rohansarkar.adhuri.Model.Data;

import java.util.ArrayList;

public class UserOpenPostData {
    private String _id;
    private String content;
    private String time;
    private String name;
    private String image;
    private String suggestorName;
    private int suggestorCount;
    private ArrayList<String> tags;

    public UserOpenPostData(String id, ArrayList<String> tags, String content, String time, String name, String image,
                            String suggestorName, int suggestorCount) {
        this._id = id;
        this.tags = tags;
        this.content = content;
        this.time = time;
        this.name = name;
        this.image = image;
        this.suggestorName = suggestorName;
        this.suggestorCount = suggestorCount;
    }

    public String get_id() {
        return _id;
    }
    public ArrayList<String> getTags() {
        return tags;
    }
    public String getContent() {
        return content;
    }
    public String getTime() {
        return time;
    }
    public String getName() {
        return name;
    }
    public String getImage() {
        return image;
    }
    public String getSuggestorName() {
        return suggestorName;
    }
    public int getSuggestorCount() {
        return suggestorCount;
    }
}

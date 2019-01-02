package in.rohansarkar.adhuri.Model.Data;

import java.io.Serializable;
import java.util.ArrayList;

public class OpenPostData implements Serializable {
    private String _id;
    private String content;
    private String time;
    private String userId;
    private String name;
    private String image;
    private String suggestorName;
    private int suggestorCount;
    private ArrayList<String> tags;

    public OpenPostData(String id, ArrayList<String> tags, String content, String time, String userId, String name, String image,
                        String suggestorName, int suggestorCount) {
        this._id = id;
        this.tags = tags;
        this.content = content;
        this.time = time;
        this.userId = userId;
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

    public String getUserId() {
        return userId;
    }
}

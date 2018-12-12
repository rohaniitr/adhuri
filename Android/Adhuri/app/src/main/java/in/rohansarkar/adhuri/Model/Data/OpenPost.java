package in.rohansarkar.adhuri.Model.Data;

import android.graphics.drawable.Drawable;

public class OpenPost {
    private String name;
    private Drawable profilePic;
    private String time;
    private String tags;
    private String content;
    private int suggCount;

    public OpenPost(String name, Drawable profilePic, String time, String tags, String content, int suggCount) {
        this.name = name;
        this.profilePic = profilePic;
        this.time = time;
        this.tags = tags;
        this.content = content;
        this.suggCount = suggCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Drawable getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(Drawable profilePic) {
        this.profilePic = profilePic;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getSuggCount() {
        return suggCount;
    }

    public void setSuggCount(int suggCount) {
        this.suggCount = suggCount;
    }
}

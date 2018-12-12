package in.rohansarkar.adhuri.Model.Data;

import android.graphics.drawable.Drawable;

public class Post {
    private String suggestId;
    private String userId;
    private String name;
    private String time;
    private String content;
    private Drawable profilePic;

    public Post(String suggestId, String userId, String name, String time, String content, Drawable profilePic) {
        this.suggestId = suggestId;
        this.userId = userId;
        this.name = name;
        this.time = time;
        this.content = content;
        this.profilePic = profilePic;
    }

    public String getSuggestId() {
        return suggestId;
    }

    public void setSuggestId(String suggestId) {
        this.suggestId = suggestId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Drawable getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(Drawable profilePic) {
        this.profilePic = profilePic;
    }
}

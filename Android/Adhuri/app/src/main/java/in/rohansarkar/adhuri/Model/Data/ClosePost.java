package in.rohansarkar.adhuri.Model.Data;

import android.graphics.drawable.Drawable;

import java.util.ArrayList;

public class ClosePost {
    private String name;
    private Drawable profilePic;
    private String time;
    private String tags;
    private String content;
    private ArrayList<Drawable> collaboratorPics;

    public ClosePost(String name, Drawable profilePic, String time, String tags, String content, ArrayList<Drawable> collaboratorPics) {
        this.name = name;
        this.profilePic = profilePic;
        this.time = time;
        this.tags = tags;
        this.content = content;
        this.collaboratorPics = collaboratorPics;
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

    public ArrayList<Drawable> getCollaboratorPics() {
        return collaboratorPics;
    }

    public void setCollaboratorPics(ArrayList<Drawable> collaboratorPics) {
        this.collaboratorPics = collaboratorPics;
    }

    public Drawable getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(Drawable profilePic) {
        this.profilePic = profilePic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

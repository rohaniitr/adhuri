package in.rohansarkar.adhuri.Model.Data;

import java.util.ArrayList;

public class UserData {
    private String _id;
    private String name;
    private String email;
    private String bio;
    private String image;
    private String gender;
    private int openPostCount;
    private int closePostCount;
    private ArrayList<String> tags;

    public UserData(String id, String name, String email, String bio, String image, String gender, int openPostCount,
                    int closePostCount, ArrayList<String> tags) {
        _id = id;
        this.name = name;
        this.email = email;
        this.bio = bio;
        this.image = image;
        this.gender = gender;
        this.openPostCount = openPostCount;
        this.closePostCount = closePostCount;
        this.tags = tags;
    }
    public UserData(String id, String name, String email, String bio, String image, String gender, ArrayList<String> tags) {
        _id = id;
        this.name = name;
        this.email = email;
        this.bio = bio;
        this.image = image;
        this.gender = gender;
        this.tags = tags;
    }

    public String get_id() {
        return _id;
    }
    public String getName() {
        return name;
    }
    public String getEmail() {
        return email;
    }
    public String getBio() {
        return bio;
    }
    public String getImage() {
        return image;
    }
    public String getGender() {
        return gender;
    }
    public ArrayList<String> getTags() {
        if(tags ==null)
            tags =new ArrayList<>();
        return tags;
    }
    public int getOpenPostCount() {
        return openPostCount;
    }
    public int getClosePostCount() {
        return closePostCount;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public void setBio(String bio) {
        this.bio = bio;
    }
    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }
}

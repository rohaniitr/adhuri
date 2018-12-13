package in.rohansarkar.adhuri.Model.Data;

import java.util.ArrayList;

public class LoginData {
    private Boolean success;
    private String name;
    private String token;
    private String email;
    private String bio;
    private String image;
    private String gender;
    private ArrayList<String> tags;

    public LoginData(String name, String token, String email, String bio, String image, String gender, ArrayList<String> tags) {
        this.name = name;
        this.token = token;
        this.email = email;
        this.bio = bio;
        this.image = image;
        this.gender = gender;
        this.tags = tags;
    }

    public String getName() {
        return name;
    }

    public String getToken() {
        return token;
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
        return tags;
    }
}

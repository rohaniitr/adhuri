package in.rohansarkar.adhuri.Model.Data;

import java.util.ArrayList;

public class LoginData extends UserData{
    private String token;

    public LoginData(String _id, String name, String token, String email, String bio, String image, String gender, int openPostCount,
            int closePostCount, ArrayList<String> tags) {
        super(_id, name, email, bio, image, gender, openPostCount, closePostCount, tags);
        this.token = token;
    }
    public LoginData(String _id, String name, String token, String email, String bio, String image, String gender, ArrayList<String> tags) {
        super(_id, name, email, bio, image, gender, 0, 0, tags);
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}

package in.rohansarkar.adhuri.Model.Data;

public class SuggestionData {
    private String _id;
    private String content;
    private String time;
    private String name;
    private String image;
    private Boolean isAccepted;

    public SuggestionData(String id, String content, String time, String name, String image, Boolean isAccepted) {
        _id = id;
        this.content = content;
        this.time = time;
        this.name = name;
        this.image = image;
        this.isAccepted = isAccepted;
    }

    public String get_id() {
        return _id;
    }
    public String getContent() {
        return content;
    }
    public String getTime() {
        return time;
    }
    public Boolean getAccepted() {
        return isAccepted;
    }
    public String getImage() {
        return image;
    }
    public String getName() {
        return name;
    }
}

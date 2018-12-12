package in.rohansarkar.adhuri.Model.Data;

public class Tag {
    private String tag;
    private boolean isSelected;

    public Tag(String tag, boolean isSelected) {
        this.tag = tag;
        this.isSelected = isSelected;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public void toggleSelected() {
        isSelected = !isSelected;
    }
}

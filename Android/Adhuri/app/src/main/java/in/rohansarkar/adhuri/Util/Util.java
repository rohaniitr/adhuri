package in.rohansarkar.adhuri.Util;

import java.util.Arrays;
import java.util.List;

public class Util {
    public static String baseUrl = "http://192.168.43.4:3000";
    public static String loginEndpoint = "/login";
    public static final int REQUEST_GET_IMAGE = 1;
    public static final String USER_IMAGE_NAME = "img.jpg";
    public static final String USER_TEMP_IMAGE_NAME = "temp_img.jpg";

    public static List<String> genderList = Arrays.asList("Male", "Female", "Unspecified");
    public static List<String> genreList = Arrays.asList("Philosophy", "Shayari", "Poem", "Quote");
}

package in.rohansarkar.adhuri.Util;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import in.rohansarkar.adhuri.Model.Data.LoginData;

public class PrefUtil {
    private static String LOG_TAG = "in.rohansarkar.adhuri.Util.PrefUtil";

    public static void setUserInfo(Context context, LoginData loginData) {
        SharedPreferences sharedPrefs = context.getSharedPreferences("user_details", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefs.edit();

        Gson gson = new Gson();
        editor.putString("user_details", gson.toJson(loginData));
        editor.commit();
    }
    public static LoginData getUserInfo(Context context) {
        SharedPreferences sharedPrefs = context.getSharedPreferences("user_details", Context.MODE_PRIVATE);
        String jsonString = sharedPrefs.getString("user_details", null);

        if(jsonString == null)
            return null;

        Gson gson = new Gson();
        return gson.fromJson(jsonString, LoginData.class);
    }
}

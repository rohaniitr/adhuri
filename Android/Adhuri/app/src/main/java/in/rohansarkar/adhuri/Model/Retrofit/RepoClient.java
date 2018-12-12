package in.rohansarkar.adhuri.Model.Retrofit;

import in.rohansarkar.adhuri.Util.Util;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RepoClient {
    private static Retrofit retrofit;
    private static final String BASE_URL = Util.baseUrl;

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
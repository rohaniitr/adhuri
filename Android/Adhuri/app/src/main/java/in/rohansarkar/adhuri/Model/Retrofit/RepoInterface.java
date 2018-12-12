package in.rohansarkar.adhuri.Model.Retrofit;

import java.util.List;

import in.rohansarkar.adhuri.Model.Data.LoginData;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface RepoInterface {
//    @GET("/photos")
//    Call<List<UserData>> getAllData();

    @Multipart
    @POST("/users/updateImage")
    Call<ResponseBody> postImage(@Part MultipartBody.Part image, @Part("upload") RequestBody name,
                                 @Header("x-access-token") String token);

    @FormUrlEncoded
    @POST("/users/login")
    Call<LoginData> loginUser(@Field("email") String email, @Field("password") String password);

    @FormUrlEncoded
    @POST("/users/register")
    Call<Boolean> registerUser(@Field("name") String name, @Field("email") String email, @Field("password") String password);

}
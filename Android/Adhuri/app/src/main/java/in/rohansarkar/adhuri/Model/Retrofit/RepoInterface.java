package in.rohansarkar.adhuri.Model.Retrofit;

import in.rohansarkar.adhuri.Model.Data.LoginData;
import in.rohansarkar.adhuri.Model.Data.SuccessData;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface RepoInterface {
    @FormUrlEncoded
    @POST("/users/login")
    Call<LoginData> loginUser(@Field("email") String email, @Field("password") String password);

    @FormUrlEncoded
    @POST("/users/register")
    Call<SuccessData> registerUser(@Field("name") String name, @Field("email") String email, @Field("password") String password);

    @FormUrlEncoded
    @POST("/users/updateDetail" )
    Call<SuccessData> saveUserInfo(@Field("name") String name, @Field("gender") String email, @Field("bio") String password,
                                   @Header("x-access-token") String token);

    @Multipart
    @POST("/users/updateImage")
    Call<ResponseBody> saveImage(@Part MultipartBody.Part image, @Part("upload") RequestBody name,
                                 @Header("x-access-token") String token);
}
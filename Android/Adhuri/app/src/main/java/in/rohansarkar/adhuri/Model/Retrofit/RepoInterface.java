package in.rohansarkar.adhuri.Model.Retrofit;

import java.util.ArrayList;

import in.rohansarkar.adhuri.Model.Data.LoginData;
import in.rohansarkar.adhuri.Model.Data.PostData;
import in.rohansarkar.adhuri.Model.Data.SuccessData;
import in.rohansarkar.adhuri.Model.Data.SuggestionData;
import in.rohansarkar.adhuri.Model.Data.ClosePostData;
import in.rohansarkar.adhuri.Model.Data.UserData;
import in.rohansarkar.adhuri.Model.Data.OpenPostData;
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
import retrofit2.http.Path;

public interface RepoInterface {
    //Auth
    @FormUrlEncoded
    @POST("/users/login")
    Call<LoginData> loginUser(@Field("email") String email, @Field("password") String password);

    @FormUrlEncoded
    @POST("/users/register")
    Call<SuccessData> registerUser(@Field("name") String name, @Field("email") String email, @Field("password") String password);

    //Feed
    @GET("/openPost/getFeed")
    Call<ArrayList<PostData>> getFeedOpenPosts(@Header("x-access-token") String token);
    @GET("/closePost/getFeed")
    Call<ArrayList<OpenPostData>> getFeedClosePosts(@Header("x-access-token") String token);

    //User
    @FormUrlEncoded
    @POST("/users/updateDetail")
    Call<SuccessData> saveUserInfo(@Field("name") String name, @Field("gender") String email, @Field("bio") String password,
                                   @Header("x-access-token") String token);

    @Multipart
    @POST("/users/updateImage")
    Call<ResponseBody> saveImage(@Part MultipartBody.Part image, @Part("upload") RequestBody name,
                                 @Header("x-access-token") String token);
    @GET("/{imageName}")
    Call<ResponseBody> downloadImage(@Path("imageName") String imageName, @Header("x-access-token") String token);

    @GET("/users/getDetail/{userId}")
    Call<UserData> getUserInfo(@Path("userId") String userId, @Header("x-access-token") String token);

    @FormUrlEncoded
    @POST("/users/updateTags" )
    Call<SuccessData> updateTags(@Field("tags")ArrayList<String> tags, @Header("x-access-token") String token);

    @GET("/openPost/getUserPosts/{userId}")
    Call<ArrayList<PostData>> getUserOpenPosts(@Path("userId") String userId, @Header("x-access-token") String token);
    @GET("/closePost/getUserPosts/{userId}")
    Call<ArrayList<ClosePostData>> getUserClosePosts(@Path("userId") String userId, @Header("x-access-token") String token);
    @GET("/suggestion/get/{postId}")
    Call<ArrayList<SuggestionData>> getSuggestions(@Path("postId") String postId, @Header("x-access-token") String token);
}
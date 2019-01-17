package in.rohansarkar.adhuri.Model.Repositories;

import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

import in.rohansarkar.adhuri.Model.Data.PostData;
import in.rohansarkar.adhuri.Model.Data.SuccessData;
import in.rohansarkar.adhuri.Model.Data.ClosePostData;
import in.rohansarkar.adhuri.Model.Data.UserData;
import in.rohansarkar.adhuri.Model.Data.OpenPostData;
import in.rohansarkar.adhuri.Model.Retrofit.RepoClient;
import in.rohansarkar.adhuri.Model.Retrofit.RepoInterface;
import in.rohansarkar.adhuri.Util.Util;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static in.rohansarkar.adhuri.Util.Util.USER_IMAGE_NAME;
import static in.rohansarkar.adhuri.Util.Util.USER_TEMP_IMAGE_NAME;

public class UserModel {
    private String LOG_TAG = this.getClass().getName();

    public void getFeedOpenPost(final String token, final MutableLiveData<ArrayList<PostData>> postData,
                                final MutableLiveData<Boolean> showLoading, final MutableLiveData<String> fragmentMessage){
        RepoInterface service = RepoClient.getRetrofitInstance().create(RepoInterface.class);
        Call<ArrayList<PostData>> call = service.getFeedOpenPosts(token);
        showLoading.setValue(true);
        fragmentMessage.setValue("Fetching Open Posts...");

        call.enqueue(new Callback<ArrayList<PostData>>() {
            @Override
            public void onResponse(Call<ArrayList<PostData>> call, Response<ArrayList<PostData>> response) {
                showLoading.setValue(false);
                if((!response.isSuccessful()) || (response.code()!=200)) {
                    fragmentMessage.setValue("There is some problem fetching posts");
                    Log.d(LOG_TAG, "getFeedOpenPost onResponse Failure : " + response.code() + " " + response.isSuccessful());
                    return;
                }

                Log.d(LOG_TAG, "getFeedOpenPost onResponse Success");
                if(response.body()==null || response.body().size()<=0) {
                    fragmentMessage.setValue("No Open Posts");
                    postData.setValue(null);
                }
                else {
                    fragmentMessage.setValue(null);
                    postData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<PostData>> call, Throwable t) {
                showLoading.setValue(false);
                fragmentMessage.setValue("Unable to fetch posts");
                Log.d(LOG_TAG, "getFeedOpenPost onFailure");
            }
        });
    }

    public void saveUserInfo(final String name, String gender, String bio, final String token,
                             final MutableLiveData<Boolean> showLoading, MutableLiveData<String> progressTitle,
                             final MutableLiveData<String> toastMessage, final MutableLiveData<Boolean> userInfoUpadated){
        RepoInterface service = RepoClient.getRetrofitInstance().create(RepoInterface.class);
        Call<SuccessData> call = service.saveUserInfo(name, gender, bio, token);
        showLoading.setValue(true);
        progressTitle.setValue("Updating User Info...");

        call.enqueue(new Callback<SuccessData>() {
            @Override
            public void onResponse(Call<SuccessData> call, Response<SuccessData> response) {
                showLoading.setValue(false);
                if((!response.isSuccessful()) || (response.code()!=200) || (!response.body().getSuccess())) {
                    toastMessage.setValue("There is some problem updating User Info");
                    Log.d(LOG_TAG, "saveUserInfo onResponse Failure : " + response.code() + " " + response.isSuccessful());
                    return;
                }

                toastMessage.setValue("User Info updated successfully");
                Log.d(LOG_TAG, "saveUserInfo onResponse Success");
                toggleValue(userInfoUpadated);
            }

            @Override
            public void onFailure(Call<SuccessData> call, Throwable t) {
                showLoading.setValue(false);
                toastMessage.setValue("Unable to update User Info");
                Log.d(LOG_TAG, "saveUserInfo onFailure");
            }
        });
    }

    public void updateTags(final ArrayList<String> tagList, final String token,
                           final MutableLiveData<Boolean> showLoading, MutableLiveData<String> loadingMessage,
                           final MutableLiveData<String> toastMessage, final MutableLiveData<Boolean> tagsUpadated){
        RepoInterface service = RepoClient.getRetrofitInstance().create(RepoInterface.class);
        Call<SuccessData> call = service.updateTags(tagList, token);
        showLoading.setValue(true);
        loadingMessage.setValue("Updating Tags...");

        call.enqueue(new Callback<SuccessData>() {
            @Override
            public void onResponse(Call<SuccessData> call, Response<SuccessData> response) {
                showLoading.setValue(false);
                if((!response.isSuccessful()) || (response.code()!=200)) {
                    toastMessage.setValue("There is some problem updating the tags");
                    Log.d(LOG_TAG, "updateTags onResponse Failure : " + response.code() + " " + response.isSuccessful());
                    return;
                }

                toastMessage.setValue("Tags updated successfully");
                Log.d(LOG_TAG, "updateTags onResponse Success");
                toggleValue(tagsUpadated);
            }

            @Override
            public void onFailure(Call<SuccessData> call, Throwable t) {
                showLoading.setValue(false);
                toastMessage.setValue("Unable to update Tags");
                Log.d(LOG_TAG, "updateTags onFailure");
            }
        });
    }

    public void uploadImage(final Context context, Bitmap imageBitmap, String token, final MutableLiveData<Boolean> showLoading,
                            MutableLiveData<String> loadingMessage, final MutableLiveData<String> toastMessage,
                            final MutableLiveData<Boolean> imageChanged){
        try {
            saveImageLocal(context, imageBitmap, context.getFilesDir()+File.separator+USER_TEMP_IMAGE_NAME);
            Log.d(LOG_TAG, USER_TEMP_IMAGE_NAME + ": ");
            File imageFile = new File(context.getFilesDir(), USER_TEMP_IMAGE_NAME);
            Log.d(LOG_TAG, USER_TEMP_IMAGE_NAME + ": " + imageFile.exists());
            RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), imageFile);
            MultipartBody.Part body = MultipartBody.Part.createFormData("upload", imageFile.getName(), reqFile);
            RequestBody name = RequestBody.create(MediaType.parse("text/plain"), "upload");

            RepoInterface service = RepoClient.getRetrofitInstance().create(RepoInterface.class);
            Call<ResponseBody> req = service.saveImage(body, name, token);
            showLoading.setValue(true);
            loadingMessage.setValue("Uploading Image...");

            req.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    showLoading.setValue(false);
                    if (!response.isSuccessful() || response.code()!=200) {
                        toastMessage.setValue("Unable to update");
                        deleteFile(context.getFilesDir()+File.separator+USER_TEMP_IMAGE_NAME);
                        return;
                    }
                    toastMessage.setValue("Uploaded Successfully!\n" + response.raw().body().toString());
                    renameFile(context.getFilesDir()+File.separator+USER_TEMP_IMAGE_NAME, context.getFilesDir()+File.separator+USER_IMAGE_NAME);
                    toggleValue(imageChanged);
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    showLoading.setValue(false);
                    toastMessage.setValue("Unable to update Picture");
                    deleteFile(context.getFilesDir()+File.separator+USER_TEMP_IMAGE_NAME);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            toastMessage.setValue("Unable to update Picture");
        }
    }

    public void getUserInfo(final String userId, final String token,
                            final MutableLiveData<UserData> userData, final MutableLiveData<Boolean> showLoading,
                            final MutableLiveData<String> loadingMessage, final MutableLiveData<String> toastMessage){
        RepoInterface service = RepoClient.getRetrofitInstance().create(RepoInterface.class);
        Call<UserData> call = service.getUserInfo(userId, token);
        showLoading.setValue(true);
        loadingMessage.setValue("Fetching User Info...");

        call.enqueue(new Callback<UserData>() {
            @Override
            public void onResponse(Call<UserData> call, Response<UserData> response) {
                showLoading.setValue(false);
                if((!response.isSuccessful()) || (response.code()!=200)) {
                    toastMessage.setValue("There is some problem finding User");
                    Log.d(LOG_TAG, "getUserInfo onResponse Failure : " + response.code() + " " + response.isSuccessful());
                    return;
                }

                Log.d(LOG_TAG, "getUserInfo onResponse Success");
                if(response.body()==null)
                    userData.setValue(null);
                else
                    userData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<UserData> call, Throwable t) {
                showLoading.setValue(false);
                Log.d(LOG_TAG, "getUserInfo onFailure");
                toastMessage.setValue("Unable to find User");
            }
        });
    }

    public void downloadImage(final Context context, String imageName, final String token, final MutableLiveData<String> toastMessage,
                              final MutableLiveData<Boolean> imageDownloaded){
        RepoInterface service = RepoClient.getRetrofitInstance().create(RepoInterface.class);
        Call<ResponseBody> call = service.downloadImage(imageName, token);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if((!response.isSuccessful()) || (response.body()==null)) {
                    toastMessage.setValue("There is some problem loading the picture");
                    Log.d(LOG_TAG, "downloadImage onResponse Failure : " + response.code() + " " + response.isSuccessful());
                    return;
                }

                // display the image data in a ImageView or save it
                Bitmap bitmapImage = BitmapFactory.decodeStream(response.body().byteStream());
                saveImageLocal(context, bitmapImage, context.getFilesDir()+File.separator+Util.USER_IMAGE_NAME);
                Log.d(LOG_TAG, "downloadImage onResponse Success");
                toggleValue(imageDownloaded);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                toastMessage.setValue("Unable to get the picture");
                Log.d(LOG_TAG, "downloadImage onFailure");
            }
        });
    }

    public void getUserOpenPost(final String userId, final String token,
                                final MutableLiveData<ArrayList<PostData>> postData,
                                final MutableLiveData<Boolean> showLoading, final MutableLiveData<String> fragmentMessage){
        RepoInterface service = RepoClient.getRetrofitInstance().create(RepoInterface.class);
        Call<ArrayList<PostData>> call = service.getUserOpenPosts(userId, token);
        showLoading.setValue(true);
        fragmentMessage.setValue("Fetching User Open Posts...");

        call.enqueue(new Callback<ArrayList<PostData>>() {
            @Override
            public void onResponse(Call<ArrayList<PostData>> call, Response<ArrayList<PostData>> response) {
                showLoading.setValue(false);
                if((!response.isSuccessful()) || (response.code()!=200)) {
                    fragmentMessage.setValue("There is some problem fetching posts");
                    Log.d(LOG_TAG, "getUserOpenPost onResponse Failure : " + response.code() + " " + response.isSuccessful());
                    return;
                }

                Log.d(LOG_TAG, "getUserOpenPost onResponse Success");
                if(response.body()==null || response.body().size()<=0) {
                    fragmentMessage.setValue("No Open Posts from User");
                    postData.setValue(null);
                }
                else {
                    fragmentMessage.setValue(null);
                    postData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<PostData>> call, Throwable t) {
                showLoading.setValue(false);
                fragmentMessage.setValue("Unable to fetch posts");
                Log.d(LOG_TAG, "getUserOpenPost onFailure");
            }
        });
    }

    public void getUserClosePost(final String userId, final String token,
                                final MutableLiveData<ArrayList<ClosePostData>> postData,
                                final MutableLiveData<Boolean> showLoading, final MutableLiveData<String> fragmentMessage){
        RepoInterface service = RepoClient.getRetrofitInstance().create(RepoInterface.class);
        Call<ArrayList<ClosePostData>> call = service.getUserClosePosts(userId, token);
        showLoading.setValue(true);
        fragmentMessage.setValue("Fetching User Close Posts...");

        call.enqueue(new Callback<ArrayList<ClosePostData>>() {
            @Override
            public void onResponse(Call<ArrayList<ClosePostData>> call, Response<ArrayList<ClosePostData>> response) {
                showLoading.setValue(false);
                if((!response.isSuccessful()) || (response.code()!=200)) {
                    fragmentMessage.setValue("There is some problem fetching posts");
                    Log.d(LOG_TAG, "getUserClosePost onResponse Failure : " + response.code() + " " + response.isSuccessful());
                    return;
                }

                Log.d(LOG_TAG, "getUserClosePost onResponse Success");
                if(response.body()==null || response.body().size()<=0) {
                    fragmentMessage.setValue("No Close Posts from User");
                    postData.setValue(null);
                }
                else {
                    fragmentMessage.setValue(null);
                    postData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<ClosePostData>> call, Throwable t) {
                showLoading.setValue(false);
                fragmentMessage.setValue("Unable to fetch posts");
                Log.d(LOG_TAG, "getUserOpenPost onFailure");
            }
        });
    }

    public void deleteAccount(MutableLiveData<Boolean> showLoading, MutableLiveData<String> progressTitle,
                              MutableLiveData<String> toastMessage, MutableLiveData<Boolean> removeUserInfo,
                              MutableLiveData<Boolean> goToLogin) {
        //ROHAN - need to implement server side
        toastMessage.setValue("Account deleted NOT SO successfully");
        removeUserInfo.setValue(true);
        goToLogin.setValue(true);
    }

    //Toggles value of Mutable<Boolean>
    private void toggleValue(MutableLiveData<Boolean> bool) {
        if(bool!=null && bool.getValue()!=null)
            bool.setValue(!bool.getValue());
        else
            bool.setValue(true);
    }
    //saves image locally
    private void saveImageLocal(Context context, Bitmap bitmap, String imagePath){
        try {
            // Assume block needs to be inside a Try/Catch block.
            OutputStream fout = null;
            File file = new File(imagePath);
            fout = new FileOutputStream(file);

            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fout); // saving the Bitmap to a file compressed as a JPEG with 85% compression rate
            fout.flush(); // Not really required
            fout.close(); // do not forget to close the stream

            MediaStore.Images.Media.insertImage(context.getContentResolver(), file.getAbsolutePath(),
                    file.getName(), file.getName());
        }catch (FileNotFoundException e){e.printStackTrace();}
        catch (IOException e){e.printStackTrace();}
    }
    private void renameFile(String fromFile, String toFile){
        if(fromFile==null || toFile==null)
            return;
        File from = new File(fromFile);
        if(from.exists()){
            File to = new File(toFile);
            from.renameTo(to);
        }
    }
    private void deleteFile(String filePath){
        File file =  new File(filePath);
        if(file.exists())
            file.delete();
    }
}
